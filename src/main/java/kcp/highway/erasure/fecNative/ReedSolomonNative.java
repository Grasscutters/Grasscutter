package kcp.highway.erasure.fecNative;

/**
 * jni调用c版本fec
 * netty的directbuf内存直接传给c层进行fec
 * 完全没有gc
 * Created by JinMiao
 * 2021/1/29.
 */
public class ReedSolomonNative{

    public static final ReedSolomonC REED_SOLOMON_C = new ReedSolomonC();

    private long reedSolomonPtr;
    private int dataShards;
    private int parityShards;


    public int getTotalShardCount(){
        return this.dataShards+this.parityShards;
    }


    public ReedSolomonNative(int dataShards, int parityShards){
        long reedSolomonPtr = REED_SOLOMON_C.rsNew(dataShards,parityShards);
        this.reedSolomonPtr = reedSolomonPtr;
        this.dataShards = dataShards;
        this.parityShards = parityShards;
    }


    public static boolean isNativeSupport() {
        return ReedSolomonC.isNativeSupport();
    }


    protected void rsRelease(){
        this.REED_SOLOMON_C.rsRelease(this.reedSolomonPtr);
    }

    protected void rsEncode(long[] shards,int byteCount){
        this.REED_SOLOMON_C.rsEncode(this.reedSolomonPtr,shards,byteCount);
    }

    protected void rsReconstruct(long[] shards,boolean[] shardPresent,int byteCount){
        this.REED_SOLOMON_C.rsReconstruct(this.reedSolomonPtr,shards,shardPresent,byteCount);
    }

    public int getDataShards() {
        return dataShards;
    }

    public void setDataShards(int dataShards) {
        this.dataShards = dataShards;
    }

    public int getParityShards() {
        return parityShards;
    }

    public void setParityShards(int parityShards) {
        this.parityShards = parityShards;
    }
}
