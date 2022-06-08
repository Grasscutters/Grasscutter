package kcp;

import io.netty.buffer.Unpooled;
import kcp.erasure.FecAdapt;
import kcp.erasure.IFecDecode;
import kcp.erasure.IFecEncode;
import kcp.erasure.fec.Fec;
import kcp.erasure.fec.FecPacket;
import kcp.erasure.fec.Snmp;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.jctools.queues.MpscLinkedQueue;
import kcp.threadPool.IMessageExecutor;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Ukcp{

    private static final InternalLogger log = InternalLoggerFactory.getInstance(Ukcp.class);


    private final IKcp kcp;

    private boolean fastFlush = true;

    private long tsUpdate = -1;

    private boolean active;

    private IFecEncode fecEncode = null;
    private IFecDecode fecDecode = null;

    private final Queue<ByteBuf> writeBuffer;

    private final Queue<ByteBuf> readBuffer;

    private final IMessageExecutor iMessageExecutor;

    private final KcpListener kcpListener;

    private final long timeoutMillis;

    private final IChannelManager channelManager;

    private final AtomicBoolean writeProcessing = new AtomicBoolean(false);

    private final AtomicBoolean readProcessing = new AtomicBoolean(false);

    private final AtomicInteger readBufferIncr = new AtomicInteger(-1);

    private final AtomicInteger writeBufferIncr = new AtomicInteger(-1);

    private final WriteTask writeTask = new WriteTask(this);

    private final ReadTask readTask = new ReadTask(this);

    private boolean controlReadBufferSize=false;

    private boolean controlWriteBufferSize=false;


    /**
     * 上次收到消息时间
     **/
    private long lastRecieveTime = System.currentTimeMillis();


    /**
     * Creates a new instance.
     *
     * @param output output for kcp
     */
    public Ukcp(KcpOutput output, KcpListener kcpListener, IMessageExecutor iMessageExecutor,  ChannelConfig channelConfig, IChannelManager channelManager) {
        this.timeoutMillis = channelConfig.getTimeoutMillis();
        this.kcp = new Kcp(channelConfig.getConv(), output);
        this.active = true;
        this.kcpListener = kcpListener;
        this.iMessageExecutor = iMessageExecutor;
        this.channelManager = channelManager;
        this.writeBuffer = new MpscLinkedQueue<>();
        this.readBuffer = new MpscLinkedQueue<>();

        if(channelConfig.getReadBufferSize()!=-1){
            this.controlReadBufferSize = true;
            this.readBufferIncr.set(channelConfig.getReadBufferSize()/channelConfig.getMtu());
        }

        if(channelConfig.getWriteBufferSize()!=-1){
            this.controlWriteBufferSize = true;
            this.writeBufferIncr.set(channelConfig.getWriteBufferSize()/channelConfig.getMtu());
        }



        int headerSize = 0;
        FecAdapt fecAdapt = channelConfig.getFecAdapt();
        if(channelConfig.isCrc32Check()){
            headerSize += ChannelConfig.crc32Size;
        }

        //init fec
        if (fecAdapt != null) {
            KcpOutput kcpOutput = kcp.getOutput();
            fecEncode = fecAdapt.fecEncode(headerSize,channelConfig.getMtu());
            fecDecode = fecAdapt.fecDecode(channelConfig.getMtu());
            kcpOutput = new FecOutPut(kcpOutput, fecEncode);
            kcp.setOutput(kcpOutput);
            headerSize+= Fec.fecHeaderSizePlus2;
        }

        kcp.setReserved(headerSize);
        initKcpConfig(channelConfig);
    }


    private void initKcpConfig(ChannelConfig channelConfig){
        kcp.nodelay(channelConfig.isNodelay(),channelConfig.getInterval(),channelConfig.getFastresend(),channelConfig.isNocwnd());
        kcp.setSndWnd(channelConfig.getSndwnd());
        kcp.setRcvWnd(channelConfig.getRcvwnd());
        kcp.setMtu(channelConfig.getMtu());
        kcp.setStream(channelConfig.isStream());
        kcp.setAckNoDelay(channelConfig.isAckNoDelay());
        kcp.setAckMaskSize(channelConfig.getAckMaskSize());
        this.fastFlush = channelConfig.isFastFlush();
    }


    /**
     * Receives ByteBufs.
     *
     * @param bufList received ByteBuf will be add to the list
     */
    protected void receive(List<ByteBuf> bufList) {
        kcp.recv(bufList);
    }


    protected ByteBuf mergeReceive() {
        return kcp.mergeRecv();
    }


    protected void input(ByteBuf data,long current) throws IOException {
        //lastRecieveTime = System.currentTimeMillis();
        Snmp.snmp.InPkts.increment();
        Snmp.snmp.InBytes.add(data.readableBytes());
        if (fecDecode != null) {
            FecPacket fecPacket = FecPacket.newFecPacket(data);
            if (fecPacket.getFlag() == Fec.typeData) {
                data.skipBytes(2);
                input(data, true,current);
            }
            if (fecPacket.getFlag() == Fec.typeData || fecPacket.getFlag() == Fec.typeParity) {
                List<ByteBuf> byteBufs = fecDecode.decode(fecPacket);
                if (byteBufs != null) {
                    ByteBuf byteBuf;
                    for (int i = 0; i < byteBufs.size(); i++) {
                        byteBuf = byteBufs.get(i);
                        input(byteBuf, false,current);
                        byteBuf.release();
                    }
                }
            }
        } else {
            input(data, true,current);
        }
    }
    private void input(ByteBuf data, boolean regular,long current) throws IOException {
        int ret = kcp.input(data, regular,current);
        switch (ret) {
            case -1:
                throw new IOException("No enough bytes of head");
            case -2:
                throw new IOException("No enough bytes of data");
            case -3:
                throw new IOException("Mismatch cmd");
            case -4:
                throw new IOException("Conv inconsistency");
            default:
                break;
        }
    }


    /**
     * Sends a Bytebuf.
     */
    void send(ByteBuf buf) throws IOException {
        int ret = kcp.send(buf);
        if (ret == -2) {
            throw new IOException("Too many fragments");
        }
    }


    /**
     * Returns {@code true} if there are bytes can be received.
     */
    protected boolean canRecv() {
        return kcp.canRecv();
    }



    protected long getLastRecieveTime() {
        return lastRecieveTime;
    }

    protected void setLastRecieveTime(long lastRecieveTime) {
        this.lastRecieveTime = lastRecieveTime;
    }

    /**
     * Returns {@code true} if the kcp can send more bytes.
     *
     * @param curCanSend last state of canSend
     * @return {@code true} if the kcp can send more bytes
     */
    protected boolean canSend(boolean curCanSend) {
        int max = kcp.getSndWnd() * 2;
        int waitSnd = kcp.waitSnd();
        if (curCanSend) {
            return waitSnd < max;
        } else {
            int threshold = Math.max(1, max / 2);
            return waitSnd < threshold;
        }
    }

    /**
     * Udpates the kcp.
     *
     * @param current current time in milliseconds
     * @return the next time to update
     */
    protected long update(long current) {
        kcp.update(current);
        long nextTsUp = check(current);
        setTsUpdate(nextTsUp);

        return nextTsUp;
    }

    protected long flush(long current){
        return kcp.flush(false,current);
    }

    /**
     * Determines when should you invoke udpate.
     *
     * @param current current time in milliseconds
     * @see Kcp#check(long)
     */
    protected long check(long current) {
        return kcp.check(current);
    }

    /**
     * Returns {@code true} if the kcp need to flush.
     *
     * @return {@code true} if the kcp need to flush
     */
    protected boolean checkFlush() {
        return kcp.checkFlush();
    }

    /**
     * Returns conv of kcp.
     *
     * @return conv of kcp
     */
    public long getConv() {
        return kcp.getConv();
    }

    /**
     * Set the conv of kcp.
     *
     * @param conv the conv of kcp
     */
    public void setConv(long conv) {
        kcp.setConv(conv);
    }


    /**
     * Returns update interval.
     *
     * @return update interval
     */
    protected int getInterval() {
        return kcp.getInterval();
    }



    protected boolean isStream() {
        return kcp.isStream();
    }



    /**
     * Sets the {@link ByteBufAllocator} which is used for the kcp to allocate buffers.
     *
     * @param allocator the allocator is used for the kcp to allocate buffers
     * @return this object
     */
    public Ukcp setByteBufAllocator(ByteBufAllocator allocator) {
        kcp.setByteBufAllocator(allocator);
        return this;
    }

    protected boolean isFastFlush() {
        return fastFlush;
    }

    protected void read(ByteBuf byteBuf) {
        if(controlReadBufferSize){
            int readBufferSize =readBufferIncr.getAndUpdate(operand -> {
                if(operand==0){
                    return operand;
                }
                return --operand;
            });
            if(readBufferSize==0){
                //TODO 这里做的不对 应该丢弃队列最早的那个消息包  这样子丢弃有一定的概率会卡死 以后优化
                byteBuf.release();
                return;
            }
        }
        this.readBuffer.offer(byteBuf);
        notifyReadEvent();
    }

    /**
     * 发送有序可靠消息
     * 线程安全的
     * @param byteBuf 发送后需要手动调用 {@link ByteBuf#release()}
     * @return true发送成功  false缓冲区满了
     */
    public boolean write(ByteBuf byteBuf) {
        if(controlWriteBufferSize){
            int bufferSize =writeBufferIncr.getAndUpdate(operand -> {
                if(operand==0){
                    return operand;
                }
                return --operand;
            });
            if(bufferSize==0){
                //log.error("conv {} address {} writeBuffer is full",kcp.getConv(),((User)kcp.getUser()).getRemoteAddress());
                return false;
            }
        }
        byteBuf = byteBuf.retainedDuplicate();
        writeBuffer.offer(byteBuf);
        notifyWriteEvent();
        return true;
    }


    protected AtomicInteger getReadBufferIncr() {
        return readBufferIncr;
    }


    /**
     * 主动关闭连接调用
     */
    public void close() {
        this.iMessageExecutor.execute(() -> internalClose());
    }

    private void notifyReadEvent() {
        if(readProcessing.compareAndSet(false,true)){
            this.iMessageExecutor.execute(this.readTask);
        }
    }

    protected void notifyWriteEvent() {
        if(writeProcessing.compareAndSet(false,true)){
            this.iMessageExecutor.execute(this.writeTask);
        }
    }


    protected long getTsUpdate() {
        return tsUpdate;
    }

    protected Queue<ByteBuf> getReadBuffer() {
        return readBuffer;
    }

    protected Ukcp setTsUpdate(long tsUpdate) {
        this.tsUpdate = tsUpdate;
        return this;
    }

    protected Queue<ByteBuf> getWriteBuffer() {
        return writeBuffer;
    }

    protected KcpListener getKcpListener() {
        return kcpListener;
    }

    public boolean isActive() {
        return active;
    }


    void internalClose() {
        if(!active){
            return;
        }
        this.active = false;
        notifyReadEvent();
        kcpListener.handleClose(this);
        //关闭之前尽量把消息都发出去
        notifyWriteEvent();
        kcp.flush(false,System.currentTimeMillis());
        //连接删除
        channelManager.del(this);
        release();
    }

    void release() {
        kcp.setState(-1);
        kcp.release();
        for (; ; ) {
            ByteBuf byteBuf = writeBuffer.poll();
            if (byteBuf == null) {
                break;
            }
            byteBuf.release();
        }
        for (; ; ) {
            ByteBuf byteBuf = readBuffer.poll();
            if (byteBuf == null) {
                break;
            }
            byteBuf.release();
        }
        if (this.fecEncode != null) {
            this.fecEncode.release();
        }

        if (this.fecDecode != null) {
            this.fecDecode.release();
        }
    }

    protected AtomicBoolean getWriteProcessing() {
        return writeProcessing;
    }


    protected AtomicBoolean getReadProcessing() {
        return readProcessing;
    }

    protected IMessageExecutor getiMessageExecutor() {
        return iMessageExecutor;
    }

    protected long getTimeoutMillis() {
        return timeoutMillis;
    }

    protected AtomicInteger getWriteBufferIncr() {
        return writeBufferIncr;
    }

    protected boolean isControlReadBufferSize() {
        return controlReadBufferSize;
    }


    protected boolean isControlWriteBufferSize() {
        return controlWriteBufferSize;
    }


    @SuppressWarnings("unchecked")
    public User user() {
        return (User) kcp.getUser();
    }

    protected Ukcp user(User user) {
        kcp.setUser(user);
        return this;
    }

    @Override
    public String toString() {
        return "Ukcp(" +
                "getConv=" + kcp.getConv() +
                ", state=" + kcp.getState() +
                ", active=" + active +
                ')';
    }
}
