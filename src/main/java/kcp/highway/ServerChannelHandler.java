package kcp.highway;

import kcp.highway.erasure.fec.Fec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kcp.highway.threadPool.IMessageExecutor;
import kcp.highway.threadPool.IMessageExecutorPool;
import kcp.highway.threadPool.ITask;

import java.net.InetSocketAddress;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by JinMiao
 * 2018/9/20.
 */
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {
    record HandshakeWaiter(long convId, InetSocketAddress address) {

    }

    private static final Logger logger = LoggerFactory.getLogger(ServerChannelHandler.class);

    private final ServerConvChannelManager channelManager;

    private final ChannelConfig channelConfig;

    private final IMessageExecutorPool iMessageExecutorPool;

    private final KcpListener kcpListener;

    private final HashedWheelTimer hashedWheelTimer;
    private final ConcurrentLinkedQueue<HandshakeWaiter> handshakeWaiters = new ConcurrentLinkedQueue<>();

    private final SecureRandom secureRandom = new SecureRandom();

    public void handshakeWaitersAppend(HandshakeWaiter handshakeWaiter) {
        if (handshakeWaiters.size() > 10) {
            handshakeWaiters.poll();
        }
        handshakeWaiters.add(handshakeWaiter);
    }

    public void handshakeWaitersRemove(HandshakeWaiter handshakeWaiter) {
        handshakeWaiters.remove(handshakeWaiter);
    }

    public HandshakeWaiter handshakeWaitersFind(long conv) {
        for (HandshakeWaiter waiter : handshakeWaiters) {
            if (waiter.convId == conv) {
                return waiter;
            }
        }
        return null;
    }

    public HandshakeWaiter handshakeWaitersFind(InetSocketAddress address) {
        for (HandshakeWaiter waiter : handshakeWaiters) {
            if (waiter.address.equals(address)) {
                return waiter;
            }
        }
        return null;
    }

    // Handle handshake
    public void handleEnet(ByteBuf data, Ukcp ukcp, User user, long conv) {
        // Get
        int code = data.readInt();
        data.readUnsignedIntLE();
        data.readUnsignedIntLE();
        int enet = data.readInt();
        data.readUnsignedInt();
        try {
            switch (code) {
                case 255 -> { // Connect + Handshake
                    if (user != null) {
                        Ukcp.sendHandshakeRsp(user, enet, conv);
                    }
                }
                case 404 -> { // Disconnect
                    if (ukcp != null) {
                        ukcp.close();
                    }
                }
            }
        } catch (Throwable ignore) {
        }
    }


    public ServerChannelHandler(IChannelManager channelManager, ChannelConfig channelConfig, IMessageExecutorPool iMessageExecutorPool, KcpListener kcpListener, HashedWheelTimer hashedWheelTimer) {
        this.channelManager = (ServerConvChannelManager) channelManager;
        this.channelConfig = channelConfig;
        this.iMessageExecutorPool = iMessageExecutorPool;
        this.kcpListener = kcpListener;
        this.hashedWheelTimer = hashedWheelTimer;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("", cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) {
        final ChannelConfig channelConfig = this.channelConfig;
        DatagramPacket msg = (DatagramPacket) object;
        ByteBuf byteBuf = msg.content();
        User user = new User(ctx.channel(), msg.sender(), msg.recipient());
        Ukcp ukcp = channelManager.get(msg);
        if (byteBuf.readableBytes() == 20) {
            // send handshake
            HandshakeWaiter waiter = handshakeWaitersFind(user.getRemoteAddress());
            long convId;
            if (waiter == null) {
                //generate unique convId
                synchronized (channelManager) {
                    do {
                        convId = secureRandom.nextLong();
                    } while (channelManager.convExists(convId) || handshakeWaitersFind(convId) != null);
                }
                handshakeWaitersAppend(new HandshakeWaiter(convId, user.getRemoteAddress()));
            } else {
                convId = waiter.convId;
            }
            handleEnet(byteBuf, ukcp, user, convId);
            msg.release();
            return;
        }
        boolean newConnection = false;
        IMessageExecutor iMessageExecutor = iMessageExecutorPool.getIMessageExecutor();
        if (ukcp == null) {// finished handshake
            HandshakeWaiter waiter = handshakeWaitersFind(byteBuf.getLong(0));
            if (waiter == null) {
                //Grasscutter.getLogger().warn("Establishing handshake to {} failure, Conv id Error", user.getRemoteAddress());
                msg.release();
                return;
            } else {
                handshakeWaitersRemove(waiter);
                int sn = getSn(byteBuf, channelConfig);
                if (sn != 0) {
                    //Grasscutter.getLogger().warn("Establishing handshake to {} failure, SN!=0", user.getRemoteAddress());
                    msg.release();
                    return;
                }
                //Grasscutter.getLogger().info("Established handshake to {} ,Conv convId={}", user.getRemoteAddress(), waiter.convId);
                KcpOutput kcpOutput = new KcpOutPutImp();
                Ukcp newUkcp = new Ukcp(kcpOutput, kcpListener, iMessageExecutor, channelConfig, channelManager);
                newUkcp.user(user);
                newUkcp.setConv(waiter.convId);
                channelManager.New(msg.sender(), newUkcp, msg);
                hashedWheelTimer.newTimeout(new ScheduleTask(iMessageExecutor, newUkcp, hashedWheelTimer),
                        newUkcp.getInterval(),
                        TimeUnit.MILLISECONDS);
                ukcp = newUkcp;
                newConnection = true;
            }
        }
        // established tunnel
        iMessageExecutor.execute(new UkcpEventSender(newConnection, ukcp, byteBuf, msg.sender()));
    }

    private static class UkcpEventSender implements ITask {
        private final boolean newConnection;
        private final Ukcp uckp;
        private final ByteBuf byteBuf;
        private final InetSocketAddress sender;

        UkcpEventSender(boolean newConnection, Ukcp ukcp, ByteBuf byteBuf, InetSocketAddress sender) {
            this.newConnection = newConnection;
            this.uckp = ukcp;
            this.byteBuf = byteBuf;
            this.sender = sender;
        }

        @Override
        public void execute() {
            if (newConnection) {
                try {
                    uckp.getKcpListener().onConnected(uckp);
                } catch (Throwable throwable) {
                    uckp.getKcpListener().handleException(throwable, uckp);
                }
            }
            uckp.user().setRemoteAddress(sender);
            uckp.read(byteBuf);
        }
    }

    private int getSn(ByteBuf byteBuf, ChannelConfig channelConfig) {
        int headerSize = 0;
        if (channelConfig.getFecAdapt() != null) {
            headerSize += Fec.fecHeaderSizePlus2;
        }
        return byteBuf.getIntLE(byteBuf.readerIndex() + Kcp.IKCP_SN_OFFSET + headerSize);
    }

}
