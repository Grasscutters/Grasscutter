package kcp.highway;

import kcp.highway.erasure.FecAdapt;
import kcp.highway.threadPool.IMessageExecutorPool;
import kcp.highway.threadPool.netty.NettyMessageExecutorPool;

/**
 * Created by JinMiao
 * 2018/9/20.
 */
public class ChannelConfig {
    public static final int crc32Size = 4;

    private long conv;
    private boolean nodelay;
    private int interval = Kcp.IKCP_INTERVAL;
    private int fastresend;
    private boolean nocwnd;
    private int sndwnd = Kcp.IKCP_WND_SND;
    private int rcvwnd = Kcp.IKCP_WND_RCV;
    private int mtu = Kcp.IKCP_MTU_DEF;
    //超时时间 超过一段时间没收到消息断开连接
    private long timeoutMillis;
    //TODO 可能有bug还未测试
    private boolean stream;

    //下面为新增参数
    private FecAdapt fecAdapt;
    //收到包立刻回传ack包
    private boolean ackNoDelay = false;
    //发送包立即调用flush 延迟低一些  cpu增加  如果interval值很小 建议关闭该参数
    private boolean fastFlush = true;
    //crc32校验
    private boolean crc32Check = false;
    //接收窗口大小(字节 -1不限制)
    private int readBufferSize = -1;
    //发送窗口大小(字节 -1不限制)
    private int writeBufferSize = -1;

    //增加ack包回复成功率 填 /8/16/32
    private int ackMaskSize = 0;
    /**
     * 使用conv确定一个channel 还是使用 socketAddress确定一个channel
     **/
    private boolean useConvChannel = false;
    /**
     * 处理kcp消息接收和发送的线程池
     **/
    private IMessageExecutorPool iMessageExecutorPool = new NettyMessageExecutorPool(Runtime.getRuntime().availableProcessors());


    public void nodelay(boolean nodelay, int interval, int resend, boolean nc) {
        this.nodelay = nodelay;
        this.interval = interval;
        this.fastresend = resend;
        this.nocwnd = nc;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public void setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
    }

    public IMessageExecutorPool getiMessageExecutorPool() {
        return iMessageExecutorPool;
    }

    public void setiMessageExecutorPool(IMessageExecutorPool iMessageExecutorPool) {
        if (this.iMessageExecutorPool != null) {
            this.iMessageExecutorPool.stop();
        }
        this.iMessageExecutorPool = iMessageExecutorPool;
    }

    public boolean isNodelay() {
        return nodelay;
    }

    public long getConv() {
        return conv;
    }

    public void setConv(long conv) {
        this.conv = conv;
    }

    public int getInterval() {
        return interval;
    }

    public int getFastresend() {
        return fastresend;
    }

    public boolean isNocwnd() {
        return nocwnd;
    }

    public int getSndwnd() {
        return sndwnd;
    }

    public void setSndwnd(int sndwnd) {
        this.sndwnd = sndwnd;
    }

    public int getRcvwnd() {
        return rcvwnd;
    }

    public void setRcvwnd(int rcvwnd) {
        this.rcvwnd = rcvwnd;
    }

    public int getMtu() {
        return mtu;
    }

    public void setMtu(int mtu) {
        this.mtu = mtu;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public FecAdapt getFecAdapt() {
        return fecAdapt;
    }

    public void setFecAdapt(FecAdapt fecAdapt) {
        this.fecAdapt = fecAdapt;
    }

    public boolean isAckNoDelay() {
        return ackNoDelay;
    }

    public void setAckNoDelay(boolean ackNoDelay) {
        this.ackNoDelay = ackNoDelay;
    }

    public boolean isFastFlush() {
        return fastFlush;
    }

    public void setFastFlush(boolean fastFlush) {
        this.fastFlush = fastFlush;
    }

    public boolean isCrc32Check() {
        return crc32Check;
    }

    public int getAckMaskSize() {
        return ackMaskSize;
    }

    public void setAckMaskSize(int ackMaskSize) {
        this.ackMaskSize = ackMaskSize;
    }

    public void setCrc32Check(boolean crc32Check) {
        this.crc32Check = crc32Check;
    }

    public boolean isUseConvChannel() {
        return useConvChannel;
    }

    public int getWriteBufferSize() {
        return writeBufferSize;
    }

    public void setWriteBufferSize(int writeBufferSize) {
        this.writeBufferSize = writeBufferSize;
    }

    public void setUseConvChannel(boolean useConvChannel) {
        this.useConvChannel = useConvChannel;
    }
}
