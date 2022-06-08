package kcp;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.Utils;
import io.netty.buffer.Unpooled;
import kcp.erasure.fec.Fec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kcp.threadPool.IMessageExecutor;
import kcp.threadPool.IMessageExecutorPool;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by JinMiao
 * 2018/9/20.
 */
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {
    static final Logger logger = LoggerFactory.getLogger(ServerChannelHandler.class);

    private final IChannelManager channelManager;

    private final ChannelConfig channelConfig;

    private final IMessageExecutorPool iMessageExecutorPool;

    private final KcpListener kcpListener;

    private final HashedWheelTimer hashedWheelTimer;

    // Handle handshake
    public static boolean handleEnet(ByteBuf data,User user,long conv) {
        if (data == null || data.readableBytes() != 20) {
            return false;
        }
        // Get
        int code = data.readInt();
        data.readUnsignedInt(); // Empty
        data.readUnsignedInt(); // Empty
        int enet = data.readInt();
        data.readUnsignedInt();
        try{
            switch (code) {
                case 255 -> { // Connect + Handshake
                    //ukcp.setConv(new Random().nextLong());
                    sendHandshakeRsp(user,enet,conv);
                }
                case 404 -> { // Disconnect
                    sendDisconnectPacket(user, 1,conv);
                    //ukcp.close();
                }
            }
        }catch (Throwable ignore){
           return false;
        }
        return true;
    }

    private static void sendHandshakeRsp(User user,int enet,long conv) throws IOException {
        ByteBuf packet = Unpooled.buffer(20);
        packet.writeInt(325);
        packet.writeIntLE((int) (conv >> 32));
        packet.writeIntLE((int) (conv & 0xFFFFFFFFL));
        packet.writeInt(enet);
        packet.writeInt(340870469); // constant?
        UDPSend(user,packet);
    }
    public static void sendDisconnectPacket(User user, int code,long conv) throws IOException {
        ByteBuf packet = Unpooled.buffer(20);
        packet.writeInt(404);
        packet.writeIntLE((int) (conv >> 32));
        packet.writeIntLE((int) (conv & 0xFFFFFFFFL));
        packet.writeInt(code);
        packet.writeInt(423728276); // constant?
        UDPSend(user,packet);
    }
    private static void UDPSend(User user,ByteBuf packet){
        DatagramPacket datagramPacket = new DatagramPacket(packet,user.getRemoteAddress(), user.getLocalAddress());
        // Send
        user.getChannel().writeAndFlush(datagramPacket);
    }

    public ServerChannelHandler(IChannelManager channelManager, ChannelConfig channelConfig, IMessageExecutorPool iMessageExecutorPool, KcpListener kcpListener,HashedWheelTimer hashedWheelTimer) {
        this.channelManager = channelManager;
        this.channelConfig = channelConfig;
        this.iMessageExecutorPool = iMessageExecutorPool;
        this.kcpListener = kcpListener;
        this.hashedWheelTimer = hashedWheelTimer;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("", cause);
        //SocketAddress socketAddress = ctx.channel().remoteAddress();
        //Ukcp ukcp = clientMap.get(socketAddress);
        //if(ukcp==null){
        //    logger.error("exceptionCaught ukcp is not exist address"+ctx.channel().remoteAddress(),cause);
        //    return;
        //}
        //ukcp.getKcpListener().handleException(cause,ukcp);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) {
        final ChannelConfig channelConfig = this.channelConfig;
        DatagramPacket msg = (DatagramPacket) object;
        Ukcp ukcp = channelManager.get(msg);
        ByteBuf byteBuf = msg.content();
        //System.out.println(Utils.bytesToHex(byteBuf));
        if (ukcp != null) {
            User user = ukcp.user();
            //每次收到消息重绑定地址
            user.setRemoteAddress(msg.sender());
            ukcp.read(byteBuf);
            //System.out.println("CONV: "+ukcp.getConv()+" RECV : "+byteBuf.readableBytes());
            return;
        }
        //如果是新连接第一个包的sn必须为0
        /*
        int sn = getSn(byteBuf,channelConfig);
        if(sn!=0){
            msg.release();
            return;
        }

        IMessageExecutor iMessageExecutor = iMessageExecutorPool.getIMessageExecutor();
        KcpOutput kcpOutput = new KcpOutPutImp();
        Ukcp newUkcp = new Ukcp(kcpOutput, kcpListener, iMessageExecutor, channelConfig, channelManager);
        User user = new User(ctx.channel(), msg.sender(), msg.recipient());
        newUkcp.user(user);
        channelManager.New(msg.sender(), newUkcp, msg);
        iMessageExecutor.execute(() -> {
            try {
                newUkcp.getKcpListener().onConnected(newUkcp);
            } catch (Throwable throwable) {
                newUkcp.getKcpListener().handleException(throwable, newUkcp);
            }
        });
        newUkcp.read(byteBuf);

        ScheduleTask scheduleTask = new ScheduleTask(iMessageExecutor, newUkcp,hashedWheelTimer);
        hashedWheelTimer.newTimeout(scheduleTask,newUkcp.getInterval(), TimeUnit.MILLISECONDS);

         */

        //handshake

        User user = new User(ctx.channel(), msg.sender(), msg.recipient());
        long NewConv = new Random().nextLong();
        if(handleEnet(byteBuf,user,NewConv)){
            KcpOutput kcpOutput = new KcpOutPutImp();
            IMessageExecutor iMessageExecutor = iMessageExecutorPool.getIMessageExecutor();
            System.out.println("handshake success! NewConv="+NewConv);
            Ukcp newUkcp = new Ukcp(kcpOutput, kcpListener, iMessageExecutor, channelConfig, channelManager);
            newUkcp.user(user);
            newUkcp.setConv(NewConv);
            channelManager.New(msg.sender(), newUkcp, msg);
            iMessageExecutor.execute(() -> {
                try {
                    newUkcp.getKcpListener().onConnected(newUkcp);
                } catch (Throwable throwable) {
                    newUkcp.getKcpListener().handleException(throwable, newUkcp);
                }
            });
            ScheduleTask scheduleTask = new ScheduleTask(iMessageExecutor, newUkcp,hashedWheelTimer);
            hashedWheelTimer.newTimeout(scheduleTask,newUkcp.getInterval(), TimeUnit.MILLISECONDS);
        }/*else{
            System.out.println("handshake failure! closing...");
            user.getChannel().close();// not a legal game client
        }*/
    }


    private int getSn(ByteBuf byteBuf,ChannelConfig channelConfig){
        int headerSize = 0;
        if(channelConfig.getFecAdapt()!=null){
            headerSize+= Fec.fecHeaderSizePlus2;
        }
        return byteBuf.getIntLE(byteBuf.readerIndex()+Kcp.IKCP_SN_OFFSET+headerSize);
    }

}
