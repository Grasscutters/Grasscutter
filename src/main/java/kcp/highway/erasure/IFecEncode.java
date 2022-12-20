package kcp.highway.erasure;

import io.netty.buffer.ByteBuf;

/**
 * Created by JinMiao
 * 2021/2/2.
 */
public interface IFecEncode {
    ByteBuf[] encode(final ByteBuf byteBuf);
    void release();
}
