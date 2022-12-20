package kcp.highway;

import io.netty.channel.socket.DatagramPacket;

import java.net.SocketAddress;
import java.util.Collection;


/**
 * Created by JinMiao
 * 2019/10/16.
 */
public interface IChannelManager {

    Ukcp get(DatagramPacket msg);

    void New(SocketAddress socketAddress,Ukcp ukcp,DatagramPacket msg);

    void del(Ukcp ukcp);

    Collection<Ukcp> getAll();
}
