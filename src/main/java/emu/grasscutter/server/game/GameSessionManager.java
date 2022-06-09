package emu.grasscutter.server.game;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import emu.grasscutter.Grasscutter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import kcp.highway.KcpListener;
import kcp.highway.Ukcp;

public class GameSessionManager {
    private static final ConcurrentHashMap<Ukcp,GameSession> sessions = new ConcurrentHashMap<>();
    private static final KcpListener listener = new KcpListener(){

        @Override
        public void onConnected(Ukcp ukcp) {
            GameSession conversation = new GameSession(Grasscutter.getGameServer());
            conversation.onConnected(new KcpTunnel(){
                @Override
                public InetSocketAddress getAddress() {
                    return ukcp.user().getRemoteAddress();
                }

                @Override
                public void writeData(byte[] bytes) {
                    ByteBuf buf = Unpooled.wrappedBuffer(bytes);
                    ukcp.write(buf);
                    buf.release();
                }

                @Override
                public void close() {
                    ukcp.close();
                }
            });
            sessions.put(ukcp,conversation);
        }

        @Override
        public void handleReceive(ByteBuf byteBuf, Ukcp ukcp) {
            GameSession conversation = sessions.get(ukcp);
            if(conversation!=null) {
                conversation.handleReceive(byteBuf);
            }
        }

        @Override
        public void handleException(Throwable ex, Ukcp ukcp) {
            sessions.get(ukcp).handleException(ex);
        }

        @Override
        public void handleClose(Ukcp ukcp) {
            GameSession conversation = sessions.get(ukcp);
            if(conversation!=null) {
                conversation.handleClose();
                sessions.remove(ukcp);
            }
        }
    };

    public static KcpListener getListener() {
        return listener;
    }

    interface KcpTunnel{
        InetSocketAddress getAddress();
        void writeData(byte[] bytes);
        void close();
    }
}
