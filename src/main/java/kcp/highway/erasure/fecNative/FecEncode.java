package kcp.highway.erasure.fecNative;

import kcp.highway.erasure.IFecEncode;
import kcp.highway.erasure.fec.Fec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 4bit(headerOffset)+4bit(seqid)+2bit(flag)+2bit(body lenth不包含自己)+body
 *
 *
 * Created by JinMiao
 * 2018/6/6.
 */
public class FecEncode implements IFecEncode {


    /**消息包长度**/
    private int dataShards;
    /**冗余包长度**/
    private int parityShards;
    /** dataShards+parityShards **/
    private int shardSize;
    //Protect Against Wrapped Sequence numbers
    private long paws;
    // next seqid
    private long next;
    //count the number of datashards collected
    private int shardCount;
    // record maximum data length in datashard
    private int maxSize;
    // FEC header offset
    private int headerOffset;
    // FEC payload offset
    private int payloadOffset;

    //private byte[][] shardCache;
    //private byte[][] encodeCache;

    //用完需要手动release
    private ByteBuf[] shardCache;
    private ByteBuf[] encodeCache;

    private ByteBuf zeros;

    private ReedSolomonNative codec;

    public FecEncode(int headerOffset, ReedSolomonNative codec,int mtu) {
        this.dataShards = codec.getDataShards();
        this.parityShards = codec.getParityShards();
        this.shardSize = this.dataShards + this.parityShards;
        //this.paws = (Integer.MAX_VALUE/shardSize - 1) * shardSize;
        this.paws = 0xffffffffL/shardSize * shardSize;
        this.headerOffset = headerOffset;
        this.payloadOffset = headerOffset + Fec.fecHeaderSize;
        this.codec =codec;

        this.shardCache = new ByteBuf[shardSize];
        this.encodeCache = new ByteBuf[parityShards];
        //for (int i = 0; i < shardCache.length; i++) {
        //    shardCache[i] = ByteBufAllocator.DEFAULT.buffer(Fec.mtuLimit);
        //}
        zeros = ByteBufAllocator.DEFAULT.buffer(mtu);
        zeros.writeBytes(new byte[mtu]);
    }



    /**
     *
     *  使用方法:
     *  1，入bytebuf后 把bytebuf发送出去,并释放bytebuf
     *  2，判断返回值是否为null，如果不为null发送出去并释放它
     *
     *  headerOffset +6字节fectHead +  2字节bodylenth(lenth-headerOffset-6)
     *
     * 1,对数据写入头标记为数据类型  markData
     * 2,写入消息长度
     * 3,获得缓存数据中最大长度，其他的缓存进行扩容到同样长度
     * 4,去掉头长度，进行fec编码
     * 5,对冗余字节数组进行标记为fec  makefec
     * 6,返回完整长度
     *
     *  注意: 传入的bytebuf如果需要释放在传入后手动释放。
     *  返回的bytebuf 也需要自己释放
     * @param byteBuf
     * @return
     */
    public ByteBuf[] encode(final ByteBuf byteBuf){
        int headerOffset = this.headerOffset;
        int payloadOffset = this.payloadOffset;
        int dataShards = this.dataShards;
        int parityShards = this.parityShards;
        ByteBuf[] shardCache = this.shardCache;
        ByteBuf[] encodeCache = this.encodeCache;
        ByteBuf zeros = this.zeros;

        markData(byteBuf,headerOffset);
        int sz = byteBuf.writerIndex();
        byteBuf.setShort(payloadOffset,sz-headerOffset- Fec.fecHeaderSizePlus2);
        shardCache[shardCount] = byteBuf.retainedDuplicate();
        shardCount ++;
        if (sz > this.maxSize) {
            this.maxSize = sz;
        }
        if(shardCount!=dataShards) {
            return null;
        }
        long[] shards = new long[dataShards+parityShards];
        //填充parityShards
        for (int i = 0; i < parityShards; i++) {
            ByteBuf parityByte = ByteBufAllocator.DEFAULT.buffer(this.maxSize);
            shardCache[i+dataShards]  = parityByte;
            encodeCache[i] = parityByte;
            markParity(parityByte,headerOffset);
            parityByte.writerIndex(this.maxSize);
            shards[i+dataShards] = parityByte.memoryAddress()+payloadOffset;
        }

        //按着最大长度不足补充0
        for (int i = 0; i < dataShards; i++) {
            ByteBuf shard = shardCache[i];
            shards[i] = shard.memoryAddress()+payloadOffset;
            int left = this.maxSize-shard.writerIndex();
            if(left<=0) {
                continue;
            }
            //是否需要扩容  会出现吗？？
            //if(shard.capacity()<this.maxSize){
            //    ByteBuf newByteBuf = ByteBufAllocator.DEFAULT.buffer(this.maxSize);
            //    newByteBuf.writeBytes(shard);
            //    shard.release();
            //    shard = newByteBuf;
            //    shardCache[i] = shard;
            //}
            shard.writeBytes(zeros,left);
            zeros.readerIndex(0);
        }
        codec.rsEncode(shards,this.maxSize-payloadOffset);
        //codec.encodeParity(shardCache,payloadOffset,this.maxSize-payloadOffset);
        //释放dataShards
        for (int i = 0; i < dataShards; i++) {
            shardCache[i].release();
            shardCache[i]=null;
        }
        this.shardCount = 0;
        this.maxSize = 0;
        return encodeCache;
    }



    public void release(){
        this.dataShards=0;
        this.parityShards=0;
        this.shardSize=0;
        this.paws=0;
        this.next=0;
        this.shardCount=0;
        this.maxSize=0;
        this.headerOffset=0;
        this.payloadOffset=0;
        ByteBuf byteBuf = null;
        for (int i = 0; i < dataShards; i++) {
            byteBuf = this.shardCache[i];
            if(byteBuf!=null) {
                byteBuf.release();
            }
        }
        zeros.release();
        codec=null;
    }

    public static void main(String[] args) {
        int a= Integer.MAX_VALUE;
        a++;
        System.out.println(a%Integer.MAX_VALUE);
        ////ReedSolomon codec = new ReedSolomon(10,3,null);
        ////FecEncode fecEncoder = new FecEncode(2,codec);
        ////for (int i = 0; i < 10; i++) {
        ////    ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10000);
        ////    byteBuf.writeInt(i);
        ////    if(i>4){
        ////        for (int i1 = i; i1 < 10; i1++) {
        ////            byteBuf.writeInt(i);
        ////        }
        ////    }
        ////    ByteBuf[] byteBufs = fecEncoder.encode(byteBuf);
        ////    if(byteBufs!=null){
        ////        System.out.println();
        ////    }
        ////}
        //ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10);
        ////byteBuf.capacity(10000);
        ////byteBuf.writeInt(55);
        //
        //ByteBuf b1 = byteBuf.retainedDuplicate();
        //
        //
        //ByteBuf writeBuf = ByteBufAllocator.DEFAULT.buffer(1000);
        //writeBuf.writeByte(22);
        //
        ////copy 拷贝  全部不相同  cap不相同
        ////duplicate  引用  值相同  readerindex writerindex不相同 cap相同
        ////slice  引用 值相同 readerindex writerindex不相同  cap不同
        ////retained  原引用计encoen数+1
        //
        //
        //ByteBuf nByteBuf = byteBuf.copy();
        //
        //
        //
        //
        //nByteBuf.setInt(0,11);
        //System.out.println(byteBuf.getInt(0));
        //System.out.println(nByteBuf.getInt(0));
        //
        ////byteBuf.writeByte(55);
        //
        ////System.out.println(byteBuf.writerIndex());
        ////System.out.println(nByteBuf.writerIndex());
        //
        //
        ////
        ////System.out.println(byteBuf.writerIndex());
        ////System.out.println(nByteBuf.writerIndex());
        //System.out.println(byteBuf.capacity());
        //System.out.println(nByteBuf.capacity());
        //
        //
        ////nByteBuf.release();
        //System.out.println(byteBuf.refCnt());
        //System.out.println(nByteBuf.refCnt());
        //
        //
        //System.out.println(writeBuf.readerIndex());
        ////writeBuf.readByte();
        //byteBuf.writeBytes(writeBuf,1);
        ////byteBuf.setBytes(0,writeBuf,1);
        //
        ////byteBuf.writeBytes(writeBuf);
        //System.out.println(byteBuf.readInt());
        //System.out.println(byteBuf.readByte());
        ////System.out.println(writeBuf.writerIndex());
    }


    private void markData(ByteBuf byteBuf,int offset){
        byteBuf.setIntLE(offset, (int) this.next);
        byteBuf.setShortLE(offset+4, Fec.typeData);
        this.next++;
    }

    private void markParity(ByteBuf byteBuf, int offset){
        byteBuf.setIntLE(offset, (int) this.next);
        byteBuf.setShortLE(offset+4,Fec.typeParity);
        //if(next==this.paws){
        //    next=0;
        //}else{
        //    next++;
        //}
        this.next = (this.next + 1) % this.paws;
        //if(next+1>=this.paws) {
        //    this.next++;
        //    //this.next = (this.next + 1) % this.paws;
        //}
    }
}
