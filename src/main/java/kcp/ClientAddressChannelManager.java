package kcp;


import io.netty.channel.socket.DatagramPacket;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by JinMiao
 * 2019/10/16.
 */
public class ClientAddressChannelManager implements IChannelManager {
    private Map<SocketAddress, Ukcp> ukcpMap = new ConcurrentHashMap<>();

    @Override
    public Ukcp get(DatagramPacket msg) {
        return ukcpMap.get(msg.recipient());
    }

    @Override
    public void New(SocketAddress socketAddress, Ukcp ukcp, DatagramPacket msg) {
        ukcpMap.put(socketAddress, ukcp);
    }


    @Override
    public void del(Ukcp ukcp) {
        ukcpMap.remove(ukcp.user().getLocalAddress());
        ukcp.user().getChannel().close();
    }

    @Override
    public Collection<Ukcp> getAll() {
        return this.ukcpMap.values();
    }
}
