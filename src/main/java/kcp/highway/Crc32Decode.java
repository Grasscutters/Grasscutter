package kcp.highway;

import kcp.highway.erasure.fec.Snmp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

/**
 * Created by JinMiao
 * 2019/12/10.
 */
public class Crc32Decode extends ChannelInboundHandlerAdapter
{
    private CRC32 crc32 = new CRC32();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof DatagramPacket) {
            DatagramPacket datagramPacket = (DatagramPacket) msg;
            ByteBuf data = datagramPacket.content();
            long checksum =  data.readUnsignedIntLE();
            ByteBuffer byteBuffer = data.nioBuffer(data.readerIndex(),data.readableBytes());
            crc32.reset();
            crc32.update(byteBuffer);
            if(checksum!=crc32.getValue()){
                Snmp.snmp.getInCsumErrors().increment();
                return;
            }
        }
       ctx.fireChannelRead(msg);
    }
}
