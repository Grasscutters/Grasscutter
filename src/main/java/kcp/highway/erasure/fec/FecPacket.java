package kcp.highway.erasure.fec;

import io.netty.buffer.ByteBuf;
import io.netty.util.Recycler;

/**
 * Created by JinMiao
 * 2018/6/26.
 */
public class FecPacket {
    private long seqid;
    private int flag;
    private ByteBuf data;
    private Recycler.Handle<FecPacket> recyclerHandle;


    private static final Recycler<FecPacket> FEC_PACKET_RECYCLER = new Recycler<FecPacket>() {
        @Override
        protected FecPacket newObject(Handle<FecPacket> handle) {
            return new FecPacket(handle);
        }
    };

    public static FecPacket newFecPacket(ByteBuf byteBuf){
        FecPacket pkt = FEC_PACKET_RECYCLER.get();
        pkt.seqid =byteBuf.readUnsignedIntLE();
        pkt.flag = byteBuf.readUnsignedShortLE();
        pkt.data = byteBuf.retainedSlice(byteBuf.readerIndex(),byteBuf.capacity()-byteBuf.readerIndex());
        pkt.data.writerIndex(byteBuf.readableBytes());
        return pkt;
    }

    private FecPacket(Recycler.Handle<FecPacket> recyclerHandle) {
        this.recyclerHandle = recyclerHandle;
    }

    public void release(){
        this.seqid = 0;
        this.flag = 0;
        this.data.release();
        this.data = null;
        recyclerHandle.recycle(this);
    }

    public long getSeqid() {
        return seqid;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public ByteBuf getData() {
        return data;
    }

    public void setData(ByteBuf data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FecPacket{" +
                "seqid=" + seqid +
                ", flag=" + flag +
                '}';
    }
}
