package kcp.highway;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.HashedWheelTimer;
import kcp.highway.erasure.fec.Fec;
import kcp.highway.threadPool.IMessageExecutor;
import kcp.highway.threadPool.IMessageExecutorPool;
import kcp.highway.threadPool.ITask;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by JinMiao
 * 2019-06-26.
 */
public class ClientChannelHandler extends ChannelInboundHandlerAdapter {
    static final Logger logger = LoggerFactory.getLogger(ClientChannelHandler.class);

    private final ClientConvChannelManager channelManager;
    private final ChannelConfig channelConfig;

    private final IMessageExecutorPool iMessageExecutorPool;

    private final KcpListener kcpListener;

    private final HashedWheelTimer hashedWheelTimer;

    // Handle handshake
    public long handleEnet(ByteBuf data, Ukcp ukcp) {
        // Get
        int code = data.readInt();
        long a = data.readUnsignedIntLE();
        long b = data.readUnsignedIntLE();
        long conv = a << 32 | b;
        data.readInt();
        data.readUnsignedInt();
        try {
            switch (code) {
                case 325 -> { // Handshake Resp
                    return conv;
                }
                case 404 -> { // Disconnect
                    if (ukcp != null) {
                        ukcp.close();
                    }
                }
            }
        } catch (Throwable ignore) {
        }
        return 0;
    }

    public ClientChannelHandler(IChannelManager channelManager, ChannelConfig channelConfig, IMessageExecutorPool iMessageExecutorPool, HashedWheelTimer hashedWheelTimer, KcpListener kcpListener) {
        this.channelManager = (ClientConvChannelManager) channelManager;
        this.channelConfig = channelConfig;
        this.iMessageExecutorPool = iMessageExecutorPool;
        this.hashedWheelTimer = hashedWheelTimer;
        this.kcpListener = kcpListener;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("", cause);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) {
        DatagramPacket msg = (DatagramPacket) object;

        ByteBuf byteBuf = msg.content();
        User user = new User(ctx.channel(), msg.sender(), msg.recipient());
        Ukcp ukcp = channelManager.get(msg);
        IMessageExecutor iMessageExecutor = iMessageExecutorPool.getIMessageExecutor();
        if (byteBuf.readableBytes() == 20) {
            // send handshake
            long convId = handleEnet(byteBuf, ukcp);
            if (convId != 0) {
                KcpOutput kcpOutput = new KcpOutPutImp();
                Ukcp newUkcp = new Ukcp(kcpOutput, kcpListener, iMessageExecutor, channelConfig, channelManager);
                newUkcp.user(user);
                newUkcp.setConv(convId);
                channelManager.New(msg.sender(), newUkcp, msg);
                hashedWheelTimer.newTimeout(new ScheduleTask(iMessageExecutor, newUkcp, hashedWheelTimer),
                        newUkcp.getInterval(),
                        TimeUnit.MILLISECONDS);
                iMessageExecutor.execute(new UkcpEventSender(true, newUkcp, byteBuf, msg.sender()));
            }
            return;
        }
        iMessageExecutor.execute(new UkcpEventSender(false, ukcp, byteBuf, msg.sender()));
    }

    static class UkcpEventSender implements ITask {
        private final boolean newConnection;
        private final Ukcp ukcp;
        private final ByteBuf byteBuf;

        UkcpEventSender(boolean newConnection, Ukcp ukcp, ByteBuf byteBuf, InetSocketAddress sender) {
            this.newConnection = newConnection;
            this.ukcp = ukcp;
            this.byteBuf = byteBuf;
        }

        @Override
        public void execute() {
            if (newConnection) {
                try {
                    ukcp.getKcpListener().onConnected(ukcp);
                } catch (Throwable throwable) {
                    ukcp.getKcpListener().handleException(throwable, ukcp);
                }
                return;
            }
            ukcp.read(byteBuf);
        }
    }
}
