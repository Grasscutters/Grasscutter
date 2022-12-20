package kcp.highway;

import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 根据conv确定一个session
 * Created by JinMiao
 * 2019/10/17.
 */
public class ServerConvChannelManager implements IChannelManager {

    private final int convIndex;

    public ServerConvChannelManager(int convIndex) {
        this.convIndex = convIndex;
    }

    private final Map<Long, Ukcp> ukcpMap = new ConcurrentHashMap<>();

    @Override
    public Ukcp get(DatagramPacket msg) {
        long conv = getConv(msg);
        return ukcpMap.get(conv);
    }

    public boolean convExists(long conv){
        return ukcpMap.containsKey(conv);
    }
    private long getConv(DatagramPacket msg) {
        ByteBuf byteBuf = msg.content();
        return byteBuf.getLong(byteBuf.readerIndex() + convIndex);
    }

    @Override
    public void New(SocketAddress socketAddress, Ukcp ukcp, DatagramPacket msg) {
        long conv = ukcp.getConv();
        if (msg != null && conv==0) {
            conv = getConv(msg);
            ukcp.setConv(conv);
        }
        ukcpMap.put(conv, ukcp);
    }

    @Override
    public void del(Ukcp ukcp) {
        ukcpMap.remove(ukcp.getConv());
    }

    @Override
    public Collection<Ukcp> getAll() {
        return this.ukcpMap.values();
    }
}
