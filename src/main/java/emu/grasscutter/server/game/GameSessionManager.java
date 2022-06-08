package emu.grasscutter.server.game;

import java.net.InetSocketAddress;
import java.util.HashMap;

import emu.grasscutter.Grasscutter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import kcp.KcpListener;
import kcp.Ukcp;

public class GameSessionManager {
    private static final HashMap<Ukcp,GameSession> sessions = new HashMap<>();
    private static final KcpListener listener = new KcpListener(){

        @Override
        public void onConnected(Ukcp ukcp) {
            sessions.put(ukcp,new GameSession(Grasscutter.getGameServer()));
            sessions.get(ukcp).onConnected(new KcpTunnel(){
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
        }

        @Override
        public void handleReceive(ByteBuf byteBuf, Ukcp ukcp) {
            sessions.get(ukcp).handleReceive(byteBuf);
        }

        @Override
        public void handleException(Throwable ex, Ukcp ukcp) {
            sessions.get(ukcp).handleException(ex);
        }

        @Override
        public void handleClose(Ukcp ukcp) {
            sessions.get(ukcp).handleClose();
            sessions.remove(ukcp);
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
