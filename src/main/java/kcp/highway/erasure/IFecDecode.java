package kcp.highway.erasure;

import kcp.highway.erasure.fec.FecPacket;
import io.netty.buffer.ByteBuf;

import java.util.List;

/**
 * Created by JinMiao
 * 2021/2/2.
 */
public interface IFecDecode {

    List<ByteBuf> decode(final FecPacket pkt);
    void release();
}
