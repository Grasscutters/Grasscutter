package kcp.highway;

import kcp.highway.erasure.fec.Snmp;
import kcp.highway.internal.ReItrLinkedList;
import kcp.highway.internal.ReusableListIterator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.Recycler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Java implementation of <a href="https://github.com/skywind3000/kcp">KCP</a>
 *
 * @author <a href="mailto:szhnet@gmail.com">szh</a>
 */
public class Kcp implements IKcp {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(Kcp.class);

    /**
     * no delay min rto
     */
    public static final int IKCP_RTO_NDL = 30;

    /**
     * normal min rto
     */
    public static final int IKCP_RTO_MIN = 100;

    public static final int IKCP_RTO_DEF = 200;

    public static final int IKCP_RTO_MAX = 60000;

    /**
     * cmd: push data
     */
    public static final byte IKCP_CMD_PUSH = 81;

    /**
     * cmd: ack
     */
    public static final byte IKCP_CMD_ACK = 82;

    /**
     * cmd: window probe (ask)
     * 询问对方当前剩余窗口大小 请求
     */
    public static final byte IKCP_CMD_WASK = 83;

    /**
     * cmd: window size (tell)
     * 返回本地当前剩余窗口大小
     */
    public static final byte IKCP_CMD_WINS = 84;

    /**
     * need to send IKCP_CMD_WASK
     */
    public static final int IKCP_ASK_SEND = 1;

    /**
     * need to send IKCP_CMD_WINS
     */
    public static final int IKCP_ASK_TELL = 2;

    public static final int IKCP_WND_SND = 256;

    public static final int IKCP_WND_RCV = 256;

    public static final int IKCP_MTU_DEF = 1400;

    public static final int IKCP_INTERVAL = 100;

    public int IKCP_OVERHEAD = 28;

    public static final int IKCP_DEADLINK = 20;

    public static final int IKCP_THRESH_INIT = 2;

    public static final int IKCP_THRESH_MIN = 2;

    /**
     * 7 secs to probe window size
     */
    public static final int IKCP_PROBE_INIT = 7000;

    /**
     * up to 120 secs to probe window
     */
    public static final int IKCP_PROBE_LIMIT = 120000;

    public static final int IKCP_SN_OFFSET = 16;


    private int ackMaskSize = 0;
    /**
     * 会话id
     **/
    private long conv;
    /**
     * 最大传输单元
     **/
    private int mtu = IKCP_MTU_DEF;
    /**
     * 最大分节大小  mtu减去头等部分
     **/
    private int mss = this.mtu - IKCP_OVERHEAD;
    /**
     * 状态
     **/
    private int state;
    /**
     * 已发送但未确认
     **/
    private long sndUna;
    /**
     * 下次发送下标
     **/
    private long sndNxt;
    /**
     * 下次接收下标
     **/
    private long rcvNxt;
    /**
     * 上次ack时间
     **/
    private long tsLastack;
    /**
     * 慢启动门限
     **/
    private int ssthresh = IKCP_THRESH_INIT;
    /**
     * RTT(Round Trip Time)
     **/
    private int rxRttval;
    /**
     * SRTT平滑RTT
     */
    private int rxSrtt;
    /**
     * RTO重传超时
     */
    private int rxRto = IKCP_RTO_DEF;
    /**
     * MinRTO最小重传超时
     */
    private int rxMinrto = IKCP_RTO_MIN;
    /**
     * 发送窗口
     **/
    private int sndWnd = IKCP_WND_SND;
    /**
     * 接收窗口
     **/
    private int rcvWnd = IKCP_WND_RCV;
    /**
     * 当前对端可接收窗口
     **/
    private int rmtWnd = IKCP_WND_RCV;

    /**
     * 拥塞控制窗口
     **/
    private int cwnd;
    /**
     * 探测标志位
     **/
    private int probe;
    ///**当前时间**/
    //private long current;
    /**
     * 间隔
     **/
    private int interval = IKCP_INTERVAL;
    /**
     * 发送
     **/
    private long tsFlush = IKCP_INTERVAL;
    /**
     * 是否无延迟 0不启用；1启用
     **/
    private boolean nodelay;
    /**
     * 状态是否已更新
     **/
    private boolean updated;
    /**
     * 探测时间
     **/
    private long tsProbe;
    /**
     * 探测等待
     **/
    private int probeWait;
    /**
     * 死连接 重传达到该值时认为连接是断开的
     **/
    private int deadLink = IKCP_DEADLINK;
    /**
     * 拥塞控制增量
     **/
    private int incr;
    /**
     * 收到包立即回ack
     **/
    private boolean ackNoDelay;

    /**
     * 待发送窗口窗口
     **/
    private LinkedList<Segment> sndQueue = new LinkedList<>();
    /**
     * 发送后待确认的队列
     **/
    private ReItrLinkedList<Segment> sndBuf = new ReItrLinkedList<>();

    /**
     * 收到后有序的队列
     **/
    private ReItrLinkedList<Segment> rcvQueue = new ReItrLinkedList<>();
    /**
     * 收到的消息 无序的
     **/
    private ReItrLinkedList<Segment> rcvBuf = new ReItrLinkedList<>();

    private ReusableListIterator<Segment> rcvQueueItr = rcvQueue.listIterator();

    public ReusableListIterator<Segment> sndBufItr = sndBuf.listIterator();

    private ReusableListIterator<Segment> rcvBufItr = rcvBuf.listIterator();

    private long[] acklist = new long[8];

    private int ackcount;

    private User user;
    /**
     * 是否快速重传 默认0关闭，可以设置2（2次ACK跨越将会直接重传）
     **/
    private int fastresend;
    /**
     * 是否关闭拥塞控制窗口
     **/
    private boolean nocwnd;
    /**
     * 是否流传输
     **/
    private boolean stream;

    /**
     * 头部预留长度  为fec checksum准备
     **/
    private int reserved;

    private KcpOutput output;

    private ByteBufAllocator byteBufAllocator = ByteBufAllocator.DEFAULT;
    /**
     * ack二进制标识
     **/
    private long ackMask;
    private long lastRcvNxt;

    private static long long2Uint(long n) {
        return n & 0x00000000FFFFFFFFL;
    }

    private static int ibound(int lower, int middle, int upper) {
        return Math.min(Math.max(lower, middle), upper);
    }

    private static int itimediff(long later, long earlier) {
        return (int) (later - earlier);
    }

    private static void output(ByteBuf data, Kcp kcp) {
        if (log.isDebugEnabled()) {
            log.debug("{} [RO] {} bytes", kcp, data.readableBytes());
        }
        if (data.readableBytes() == 0) {
            return;
        }
        kcp.output.out(data, kcp);
    }

    private static int encodeSeg(ByteBuf buf, Segment seg) {
        int offset = buf.writerIndex();

        buf.writeLong(seg.conv);
        buf.writeByte(seg.cmd);
        buf.writeByte(seg.frg);
        buf.writeShortLE(seg.wnd);
        buf.writeIntLE((int) seg.ts);
        buf.writeIntLE((int) seg.sn);
        buf.writeIntLE((int) seg.una);
        int dataSize = seg.data == null ? 0 : seg.data.readableBytes();
        buf.writeIntLE(dataSize);
        switch (seg.ackMaskSize) {
            case 8 -> buf.writeByte((int) seg.ackMask);
            case 16 -> buf.writeShortLE((int) seg.ackMask);
            case 32 -> buf.writeIntLE((int) seg.ackMask);
            case 64 -> buf.writeLongLE(seg.ackMask);
        }
        Snmp.snmp.OutSegs.increment();
        return buf.writerIndex() - offset;
    }

    public Kcp(long conv, KcpOutput output) {
        this.conv = conv;
        this.output = output;
    }

    @Override
    public void release() {
        release(sndBuf);
        release(rcvBuf);
        release(sndQueue);
        release(rcvQueue);
    }

    private void release(List<Segment> segQueue) {
        for (Segment seg : segQueue) {
            seg.recycle(true);
        }
    }

    private ByteBuf createFlushByteBuf() {
        return byteBufAllocator.ioBuffer(this.mtu);
    }


    @Override
    public ByteBuf mergeRecv() {
        if (rcvQueue.isEmpty()) {
            return null;
        }
        int peekSize = peekSize();

        if (peekSize < 0) {
            return null;
        }


        boolean recover = false;
        if (rcvQueue.size() >= rcvWnd) {
            recover = true;
        }
        ByteBuf byteBuf = null;

        // merge fragment
        int len = 0;
        for (Iterator<Segment> itr = rcvQueueItr.rewind(); itr.hasNext(); ) {
            Segment seg = itr.next();
            len += seg.data.readableBytes();
            int fragment = seg.frg;
            itr.remove();

            // log
            if (log.isDebugEnabled()) {
                log.debug("{} recv sn={}", this, seg.sn);
            }
            if (byteBuf == null) {
                if (fragment == 0) {
                    byteBuf = seg.data;
                    seg.recycle(false);
                    break;
                }
                byteBuf = byteBufAllocator.ioBuffer(len);
            }
            byteBuf.writeBytes(seg.data);
            seg.recycle(true);
            if (fragment == 0) {
                break;
            }
        }

        assert len == peekSize;

        // move available data from rcv_buf -> rcv_queue
        moveRcvData();

        // fast recover
        if (rcvQueue.size() < rcvWnd && recover) {
            // ready to send back IKCP_CMD_WINS in ikcp_flush
            // tell remote my window size
            probe |= IKCP_ASK_TELL;
        }

        return byteBuf;
    }


    /**
     * 1，判断是否有完整的包，如果有就抛给下一层
     * 2，整理消息接收队列，判断下一个包是否已经收到 收到放入rcvQueue
     * 3，判断接收窗口剩余是否改变，如果改变记录需要通知
     *
     * @param bufList
     * @return
     */
    @Override
    public int recv(List<ByteBuf> bufList) {
        if (rcvQueue.isEmpty()) {
            return -1;
        }
        int peekSize = peekSize();

        if (peekSize < 0) {
            return -2;
        }
        //接收队列长度大于接收窗口？比如接收窗口是32个包，目前已经满32个包了，需要在恢复的时候告诉对方
        boolean recover = false;
        if (rcvQueue.size() >= rcvWnd) {
            recover = true;
        }

        // merge fragment
        int len = 0;
        for (Iterator<Segment> itr = rcvQueueItr.rewind(); itr.hasNext(); ) {
            Segment seg = itr.next();
            len += seg.data.readableBytes();
            bufList.add(seg.data);

            int fragment = seg.frg;

            // log
            if (log.isDebugEnabled()) {
                log.debug("{} recv sn={}", this, seg.sn);
            }

            itr.remove();
            seg.recycle(false);

            if (fragment == 0) {
                break;
            }
        }

        assert len == peekSize;

        // move available data from rcv_buf -> rcv_queue
        moveRcvData();

        // fast recover接收队列长度小于接收窗口，说明还可以接数据，已经恢复了，在下次发包的时候告诉对方本方的窗口
        if (rcvQueue.size() < rcvWnd && recover) {
            // ready to send back IKCP_CMD_WINS in ikcp_flush
            // tell remote my window size
            probe |= IKCP_ASK_TELL;
        }

        return len;
    }

    /**
     * check the size of next message in the recv queue
     * 检查接收队列里面是否有完整的一个包，如果有返回该包的字节长度
     *
     * @return -1 没有完整包， >0 一个完整包所含字节
     */
    @Override
    public int peekSize() {
        if (rcvQueue.isEmpty()) {
            return -1;
        }

        Segment seg = rcvQueue.peek();
        //第一个包是一条应用层消息的最后一个分包？一条消息只有一个包的情况？
        if (seg.frg == 0) {
            return seg.data.readableBytes();
        }
        //接收队列长度小于应用层消息分包数量？接收队列空间不够用于接收完整的一个消息？
        if (rcvQueue.size() < seg.frg + 1) { // Some segments have not arrived yet
            return -1;
        }

        int len = 0;
        for (Iterator<Segment> itr = rcvQueueItr.rewind(); itr.hasNext(); ) {
            Segment s = itr.next();
            len += s.data.readableBytes();
            if (s.frg == 0) {
                break;
            }
        }

        return len;
    }

    /**
     * 判断一条消息是否完整收全了
     *
     * @return
     */
    @Override
    public boolean canRecv() {
        if (rcvQueue.isEmpty()) {
            return false;
        }

        Segment seg = rcvQueue.peek();
        if (seg.frg == 0) {
            return true;
        }

        if (rcvQueue.size() < seg.frg + 1) { // Some segments have not arrived yet
            return false;
        }

        return true;
    }


    @Override
    public int send(ByteBuf buf) {
        assert mss > 0;

        int len = buf.readableBytes();
        if (len == 0) {
            return -1;
        }

        // append to previous segment in streaming mode (if possible)
        if (stream) {
            if (!sndQueue.isEmpty()) {
                Segment last = sndQueue.peekLast();
                ByteBuf lastData = last.data;
                int lastLen = lastData.readableBytes();
                if (lastLen < mss) {
                    int capacity = mss - lastLen;
                    int extend = len < capacity ? len : capacity;
                    if (lastData.maxWritableBytes() < extend) { // extend
                        ByteBuf newBuf = byteBufAllocator.ioBuffer(lastLen + extend);
                        newBuf.writeBytes(lastData);
                        lastData.release();
                        lastData = last.data = newBuf;
                    }
                    lastData.writeBytes(buf, extend);

                    len = buf.readableBytes();
                    if (len == 0) {
                        return 0;
                    }
                }
            }
        }

        int count;
        if (len <= mss) {
            count = 1;
        } else {
            count = (len + mss - 1) / mss;
        }

        if (count > 255) { // Maybe don't need the conditon in stream mode
            return -2;
        }

        if (count == 0) { // impossible
            count = 1;
        }

        // segment
        for (int i = 0; i < count; i++) {
            int size = len > mss ? mss : len;
            Segment seg = Segment.createSegment(buf.readRetainedSlice(size));
            seg.frg = (short) (stream ? 0 : count - i - 1);
            sndQueue.add(seg);
            len = buf.readableBytes();
        }

        return 0;
    }

    /**
     * update ack.
     * parse ack根据RTT计算SRTT和RTO即重传超时
     *
     * @param rtt
     */
    private void updateAck(int rtt) {
        if (rxSrtt == 0) {
            rxSrtt = rtt;
            rxRttval = rtt >> 2;
        } else {
            int delta = rtt - rxSrtt;
            rxSrtt += delta >> 3;
            delta = Math.abs(delta);
            if (rtt < rxSrtt - rxRttval) {
                rxRttval += (delta - rxRttval) >> 5;
            } else {
                rxRttval += (delta - rxRttval) >> 2;
            }
            //int delta = rtt - rxSrtt;
            //if (delta < 0) {
            //    delta = -delta;
            //}
            //rxRttval = (3 * rxRttval + delta) / 4;
            //rxSrtt = (7 * rxSrtt + rtt) / 8;
            //if (rxSrtt < 1) {
            //    rxSrtt = 1;
            //}
        }
        int rto = rxSrtt + Math.max(interval, rxRttval << 2);
        rxRto = ibound(rxMinrto, rto, IKCP_RTO_MAX);
    }

    private void shrinkBuf() {
        if (sndBuf.size() > 0) {
            Segment seg = sndBuf.peek();
            sndUna = seg.sn;
        } else {
            sndUna = sndNxt;
        }
    }

    private void parseAck(long sn) {
        if (itimediff(sn, sndUna) < 0 || itimediff(sn, sndNxt) >= 0) {
            return;
        }

        for (Iterator<Segment> itr = sndBufItr.rewind(); itr.hasNext(); ) {
            Segment seg = itr.next();
            if (sn == seg.sn) {
                itr.remove();
                seg.recycle(true);
                break;
            }
            if (itimediff(sn, seg.sn) < 0) {
                break;
            }
        }
    }

    private int parseUna(long una) {
        int count = 0;
        for (Iterator<Segment> itr = sndBufItr.rewind(); itr.hasNext(); ) {
            Segment seg = itr.next();
            if (itimediff(una, seg.sn) > 0) {
                count++;
                itr.remove();
                seg.recycle(true);
            } else {
                break;
            }
        }
        return count;
    }

    private void parseAckMask(long una, long ackMask) {
        if (ackMask == 0) {
            return;
        }
        for (Iterator<Segment> itr = sndBufItr.rewind(); itr.hasNext(); ) {
            Segment seg = itr.next();
            long index = seg.sn - una - 1;
            if (index < 0) {
                continue;
            }
            if (index >= ackMaskSize) {
                break;
            }
            long mask = ackMask & 1 << index;
            if (mask != 0) {
                itr.remove();
                seg.recycle(true);
            }
        }
    }


    private void parseFastack(long sn, long ts) {
        if (itimediff(sn, sndUna) < 0 || itimediff(sn, sndNxt) >= 0) {
            return;
        }

        for (Iterator<Segment> itr = sndBufItr.rewind(); itr.hasNext(); ) {
            Segment seg = itr.next();
            if (itimediff(sn, seg.sn) < 0) {
                break;
                //根据时间判断  在当前包时间之前的包才能被认定是需要快速重传的
            } else if (sn != seg.sn && itimediff(seg.ts, ts) <= 0) {
                seg.fastack++;
            }
        }
    }

    private void ackPush(long sn, long ts) {
        int newSize = 2 * (ackcount + 1);

        if (newSize > acklist.length) {
            int newCapacity = acklist.length << 1; // double capacity

            if (newCapacity < 0) {
                throw new OutOfMemoryError();
            }

            long[] newArray = new long[newCapacity];
            System.arraycopy(acklist, 0, newArray, 0, acklist.length);
            this.acklist = newArray;
        }

        acklist[2 * ackcount] = sn;
        acklist[2 * ackcount + 1] = ts;
        ackcount++;
    }

    private boolean parseData(Segment newSeg) {
        long sn = newSeg.sn;

        if (itimediff(sn, rcvNxt + rcvWnd) >= 0 || itimediff(sn, rcvNxt) < 0) {
            newSeg.recycle(true);
            return true;
        }

        boolean repeat = false;
        boolean findPos = false;
        ListIterator<Segment> listItr = null;
        if (rcvBuf.size() > 0) {
            listItr = rcvBufItr.rewind(rcvBuf.size());
            while (listItr.hasPrevious()) {
                Segment seg = listItr.previous();
                if (seg.sn == sn) {
                    repeat = true;
                    //Snmp.snmp.RepeatSegs.incrementAndGet();
                    break;
                }
                if (itimediff(sn, seg.sn) > 0) {
                    findPos = true;
                    break;
                }
            }
        }

        if (repeat) {
            newSeg.recycle(true);
        } else if (listItr == null) {
            rcvBuf.add(newSeg);
        } else {
            if (findPos) {
                listItr.next();
            }
            listItr.add(newSeg);
        }

        // move available data from rcv_buf -> rcv_queue
        moveRcvData(); // Invoke the method only if the segment is not repeat?
        return repeat;
    }


    private void moveRcvData() {
        for (Iterator<Segment> itr = rcvBufItr.rewind(); itr.hasNext(); ) {
            Segment seg = itr.next();
            if (seg.sn == rcvNxt && rcvQueue.size() < rcvWnd) {
                itr.remove();
                rcvQueue.add(seg);
                rcvNxt++;
            } else {
                break;
            }
        }
    }


    @Override
    public int input(ByteBuf data, boolean regular, long current) {
        long oldSndUna = sndUna;
        if (data == null) return -1;

        if (data.readableBytes() < IKCP_OVERHEAD) return -1;

        if (log.isDebugEnabled()) {
            log.debug("{} [RI] {} bytes", this, data.readableBytes());
        }


        long latest = 0; // latest packet
        boolean flag = false;
        int inSegs = 0;
        boolean windowSlides = false;

        long uintCurrent = long2Uint(currentMs(current));

        while (true) {
            long conv;
            int len, wnd;
            long ts, sn, una, ackMask;
            byte cmd;
            short frg;
            Segment seg;

            if (data.readableBytes() < IKCP_OVERHEAD) {
                break;
            }

            conv = data.readLong();

            if (conv != this.conv) {
                return -4;
            }

            cmd = data.readByte();
            frg = data.readUnsignedByte();
            wnd = data.readUnsignedShortLE();
            ts = data.readIntLE();
            sn = data.readUnsignedIntLE();
            una = data.readUnsignedIntLE();
            len = data.readIntLE();

            ackMask = switch (ackMaskSize) {
                case 8 -> data.readUnsignedByte();
                case 16 -> data.readUnsignedShortLE();
                case 32 -> data.readUnsignedIntLE();
                case 64 ->
                    //TODO need unsignedLongLe
                        data.readLongLE();
                default -> 0;
            };

            if (data.readableBytes() < len || len < 0) {
                return -2;
            }

            if (cmd != IKCP_CMD_PUSH && cmd != IKCP_CMD_ACK && cmd != IKCP_CMD_WASK && cmd != IKCP_CMD_WINS) {
                return -3;
            }

            //最后收到的来计算远程窗口大小
            if (regular) {
                this.rmtWnd = wnd;//更新远端窗口大小删除已确认的包，una以前的包对方都收到了，可以把本地小于una的都删除掉
            }

            //this.rmtWnd = wnd;
            if (parseUna(una) > 0) {
                windowSlides = true;
            }
            shrinkBuf();


            boolean readed = false;
            switch (cmd) {
                case IKCP_CMD_ACK: {
                    parseAck(sn);
                    parseFastack(sn, ts);
                    flag = true;
                    latest = ts;
                    int rtt = itimediff(uintCurrent, ts);
                    if (log.isDebugEnabled()) {
                        log.debug("{} input ack: sn={}, rtt={}, rto={} ,regular={} ts={}", this, sn, rtt, rxRto, regular, ts);
                    }
                    break;
                }
                case IKCP_CMD_PUSH: {
                    boolean repeat = true;
                    if (itimediff(sn, rcvNxt + rcvWnd) < 0) {
                        ackPush(sn, ts);
                        if (itimediff(sn, rcvNxt) >= 0) {
                            if (len > 0) {
                                seg = Segment.createSegment(data.readRetainedSlice(len));
                                readed = true;
                            } else {
                                seg = Segment.createSegment(byteBufAllocator, 0);
                            }
                            seg.conv = conv;
                            seg.cmd = cmd;
                            seg.frg = frg;
                            seg.wnd = wnd;
                            seg.ts = ts;
                            seg.sn = sn;
                            seg.una = una;
                            repeat = parseData(seg);
                        }
                    }
                    if (regular && repeat) {
                        Snmp.snmp.RepeatSegs.increment();
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("{} input push: sn={}, una={}, ts={},regular={}", this, sn, una, ts, regular);
                    }
                    break;
                }
                case IKCP_CMD_WASK: {
                    // ready to send back IKCP_CMD_WINS in ikcp_flush
                    // tell remote my window size
                    probe |= IKCP_ASK_TELL;
                    if (log.isDebugEnabled()) {
                        log.debug("{} input ask", this);
                    }
                    break;
                }
                case IKCP_CMD_WINS: {
                    // do nothing
                    if (log.isDebugEnabled()) {
                        log.debug("{} input tell: {}", this, wnd);
                    }
                    break;
                }
                default:
                    return -3;
            }

            parseAckMask(una, ackMask);

            if (!readed) {
                data.skipBytes(len);
            }
            inSegs++;
        }

        Snmp.snmp.InSegs.add(inSegs);

        if (flag && regular) {
            int rtt = itimediff(uintCurrent, latest);
            if (rtt >= 0) {
                updateAck(rtt);//收到ack包，根据ack包的时间计算srtt和rto
            }

        }

        if (!nocwnd) {
            if (itimediff(sndUna, oldSndUna) > 0) {
                if (cwnd < rmtWnd) {
                    int mss = this.mss;
                    if (cwnd < ssthresh) {
                        cwnd++;
                        incr += mss;
                    } else {
                        if (incr < mss) {
                            incr = mss;
                        }
                        incr += (mss * mss) / incr + (mss / 16);
                        if ((cwnd + 1) * mss <= incr) {
                            if (mss > 0) {
                                cwnd = (incr + mss - 1) / mss;
                            } else {
                                cwnd = incr + mss - 1;
                            }
                        }
                    }
                    if (cwnd > rmtWnd) {
                        cwnd = rmtWnd;
                        incr = rmtWnd * mss;
                    }
                }
            }
        }
        //TODO 这里每个包立即去flush会造成大量小包网络利用率低，readtask里面在读完数据之后统一处理性能好一些
        //if(windowSlides){
        //    flush(false,current);
        //}else
        //    if (ackNoDelay && ackcount > 0) { // ack immediately
        //    flush(true,current);
        //}
        return 0;
    }

    private int wndUnused() {
        if (rcvQueue.size() < rcvWnd) {
            return rcvWnd - rcvQueue.size();
        }
        return 0;
    }


    private ByteBuf makeSpace(ByteBuf buffer, int space) {
        if (buffer == null) {
            buffer = createFlushByteBuf();
            buffer.writerIndex(reserved);
        } else if (buffer.readableBytes() + space > mtu) {
            output(buffer, this);
            buffer = createFlushByteBuf();
            buffer.writerIndex(reserved);
        }
        return buffer;
    }

    private void flushBuffer(ByteBuf buffer) {
        if (buffer == null)
            return;
        if (buffer.readableBytes() > reserved) {
            output(buffer, this);
            return;
        }
        buffer.release();

    }


    private long startTicks = System.currentTimeMillis();

    @Override
    public long currentMs(long now) {
        return now - startTicks;
    }


    /**
     * ikcp_flush
     */
    @Override
    public long flush(boolean ackOnly, long current) {

        // 'ikcp_update' haven't been called.
        //if (!updated) {
        //    return;
        //}

        //long current = this.current;
        //long uintCurrent = long2Uint(current);
        current = currentMs(current);

        Segment seg = Segment.createSegment(byteBufAllocator, 0);
        seg.conv = conv;
        seg.cmd = IKCP_CMD_ACK;
        seg.ackMaskSize = this.ackMaskSize;
        seg.wnd = wndUnused();//可接收数量
        seg.una = rcvNxt;//已接收数量，下次要接收的包的sn，这sn之前的包都已经收到

        //flush时候没有数据发送无需创建buffer
        ByteBuf buffer = null;
        //计算ackMask
        int count = ackcount;

        if (lastRcvNxt != rcvNxt) {
            ackMask = 0;
            lastRcvNxt = rcvNxt;
        }
        for (int i = 0; i < count; i++) {
            long sn = acklist[i * 2];
            if (sn < rcvNxt) {
                continue;
            }
            long index = sn - rcvNxt - 1;
            if (index >= ackMaskSize) {
                break;
            }
            if (index >= 0) {
                ackMask |= 1 << index;
            }
        }

        seg.ackMask = ackMask;


        // flush acknowledges有收到的包需要确认，则发确认包
        for (int i = 0; i < count; i++) {
            long sn = acklist[i * 2];
            if (itimediff(sn, rcvNxt) >= 0 || count - 1 == i) {
                buffer = makeSpace(buffer, IKCP_OVERHEAD);
                seg.sn = sn;
                seg.ts = acklist[i * 2 + 1];
                encodeSeg(buffer, seg);

                if (log.isDebugEnabled()) {
                    log.debug("{} flush ack: sn={}, ts={} ,count={}", this, seg.sn, seg.ts, count);
                }
            }
        }

        ackcount = 0;


        if (ackOnly) {
            flushBuffer(buffer);
            seg.recycle(true);
            return interval;
        }

        // probe window size (if remote window size equals zero)
        //拥堵控制 如果对方可接受窗口大小为0  需要询问对方窗口大小
        if (rmtWnd == 0) {
            if (probeWait == 0) {
                probeWait = IKCP_PROBE_INIT;
                tsProbe = current + probeWait;
            } else {
                if (itimediff(current, tsProbe) >= 0) {
                    if (probeWait < IKCP_PROBE_INIT) {
                        probeWait = IKCP_PROBE_INIT;
                    }
                    probeWait += probeWait / 2;
                    if (probeWait > IKCP_PROBE_LIMIT) {
                        probeWait = IKCP_PROBE_LIMIT;
                    }
                    tsProbe = current + probeWait;
                    probe |= IKCP_ASK_SEND;
                }
            }
        } else {
            tsProbe = 0;
            probeWait = 0;
        }

        // flush window probing commands
        if ((probe & IKCP_ASK_SEND) != 0) {
            seg.cmd = IKCP_CMD_WASK;
            buffer = makeSpace(buffer, IKCP_OVERHEAD);
            encodeSeg(buffer, seg);
            if (log.isDebugEnabled()) {
                log.debug("{} flush ask", this);
            }
        }

        // flush window probing commands
        if ((probe & IKCP_ASK_TELL) != 0) {
            seg.cmd = IKCP_CMD_WINS;
            buffer = makeSpace(buffer, IKCP_OVERHEAD);
            encodeSeg(buffer, seg);
            if (log.isDebugEnabled()) {
                log.debug("{} flush tell: wnd={}", this, seg.wnd);
            }
        }

        probe = 0;

        // calculate window size
        int cwnd0 = Math.min(sndWnd, rmtWnd);
        if (!nocwnd) {
            cwnd0 = Math.min(this.cwnd, cwnd0);
        }

        int newSegsCount = 0;
        // move data from snd_queue to snd_buf
        while (itimediff(sndNxt, sndUna + cwnd0) < 0) {
            Segment newSeg = sndQueue.poll();
            if (newSeg == null) {
                break;
            }
            newSeg.conv = conv;
            newSeg.cmd = IKCP_CMD_PUSH;
            newSeg.sn = sndNxt;
            sndBuf.add(newSeg);
            sndNxt++;
            newSegsCount++;
        }

        // calculate resent
        int resent = fastresend > 0 ? fastresend : Integer.MAX_VALUE;

        // flush data segments
        int change = 0;
        boolean lost = false;
        int lostSegs = 0, fastRetransSegs = 0, earlyRetransSegs = 0;
        long minrto = interval;


        for (Iterator<Segment> itr = sndBufItr.rewind(); itr.hasNext(); ) {
            Segment segment = itr.next();
            boolean needsend = false;
            if (segment.xmit == 0) {
                needsend = true;
                segment.rto = rxRto;
                segment.resendts = current + segment.rto;
                if (log.isDebugEnabled()) {
                    log.debug("{} flush data: sn={}, resendts={}", this, segment.sn, (segment.resendts - current));
                }
            } else if (segment.fastack >= resent) {
                needsend = true;
                segment.fastack = 0;
                segment.rto = rxRto;
                segment.resendts = current + segment.rto;
                change++;
                fastRetransSegs++;
                if (log.isDebugEnabled()) {
                    log.debug("{} fastresend. sn={}, xmit={}, resendts={} ", this, segment.sn, segment.xmit, (segment
                            .resendts - current));
                }
            } else if (segment.fastack > 0 && newSegsCount == 0) {  // early retransmit
                needsend = true;
                segment.fastack = 0;
                segment.rto = rxRto;
                segment.resendts = current + segment.rto;
                change++;
                earlyRetransSegs++;
            } else if (itimediff(current, segment.resendts) >= 0) {
                needsend = true;
                if (!nodelay) {
                    segment.rto += rxRto;
                } else {
                    segment.rto += rxRto / 2;
                }
                segment.fastack = 0;
                segment.resendts = current + segment.rto;
                lost = true;
                lostSegs++;
                if (log.isDebugEnabled()) {
                    log.debug("{} resend. sn={}, xmit={}, resendts={}", this, segment.sn, segment.xmit, (segment
                            .resendts - current));
                }
            }


            if (needsend) {
                segment.xmit++;
                segment.ts = long2Uint(current);
                segment.wnd = seg.wnd;
                segment.una = rcvNxt;
                segment.ackMaskSize = this.ackMaskSize;
                segment.ackMask = ackMask;

                ByteBuf segData = segment.data;
                int segLen = segData.readableBytes();
                int need = IKCP_OVERHEAD + segLen;
                buffer = makeSpace(buffer, need);
                encodeSeg(buffer, segment);

                if (segLen > 0) {
                    // don't increases data's readerIndex, because the data may be resend.
                    buffer.writeBytes(segData, segData.readerIndex(), segLen);
                }

                //if (segment.xmit >= deadLink) {
                //    state = -1;
                //}

                // get the nearest rto
                long rto = itimediff(segment.resendts, current);
                if (rto > 0 && rto < minrto) {
                    minrto = rto;
                }
            }
        }

        // flash remain segments
        flushBuffer(buffer);
        seg.recycle(true);

        int sum = lostSegs;
        if (lostSegs > 0) {
            Snmp.snmp.LostSegs.add(lostSegs);
        }
        if (fastRetransSegs > 0) {
            Snmp.snmp.FastRetransSegs.add(fastRetransSegs);
            sum += fastRetransSegs;
        }
        if (earlyRetransSegs > 0) {
            Snmp.snmp.EarlyRetransSegs.add(earlyRetransSegs);
            sum += earlyRetransSegs;
        }
        if (sum > 0) {
            Snmp.snmp.RetransSegs.add(sum);
        }
        // update ssthresh
        if (!nocwnd) {
            if (change > 0) {
                int inflight = (int) (sndNxt - sndUna);
                ssthresh = inflight / 2;
                if (ssthresh < IKCP_THRESH_MIN) {
                    ssthresh = IKCP_THRESH_MIN;
                }
                cwnd = ssthresh + resent;
                incr = cwnd * mss;
            }

            if (lost) {
                ssthresh = cwnd0 / 2;
                if (ssthresh < IKCP_THRESH_MIN) {
                    ssthresh = IKCP_THRESH_MIN;
                }
                cwnd = 1;
                incr = mss;
            }

            if (cwnd < 1) {
                cwnd = 1;
                incr = mss;
            }
        }
        return minrto;
    }

    /**
     * update getState (call it repeatedly, every 10ms-100ms), or you can ask
     * ikcp_check when to call it again (without ikcp_input/_send calling).
     * 'current' - current timestamp in millisec.
     *
     * @param current
     */
    @Override
    public void update(long current) {

        if (!updated) {
            updated = true;
            tsFlush = current;
        }

        int slap = itimediff(current, tsFlush);

        if (slap >= 10000 || slap < -10000) {
            tsFlush = current;
            slap = 0;
        }

        /*if (slap >= 0) {
            tsFlush += setInterval;
            if (itimediff(this.current, tsFlush) >= 0) {
                tsFlush = this.current + setInterval;
            }
            flush();
        }*/

        if (slap >= 0) {
            tsFlush += interval;
            if (itimediff(current, tsFlush) >= 0) {
                tsFlush = current + interval;
            }
        } else {
            tsFlush = current + interval;
        }
        flush(false, current);
    }

    /**
     * Determine when should you invoke ikcp_update:
     * returns when you should invoke ikcp_update in millisec, if there
     * is no ikcp_input/_send calling. you can call ikcp_update in that
     * time, instead of call update repeatly.
     * Important to reduce unnacessary ikcp_update invoking. use it to
     * schedule ikcp_update (eg. implementing an epoll-like mechanism,
     * or optimize ikcp_update when handling massive kcp connections)
     *
     * @param current
     * @return
     */
    @Override
    public long check(long current) {
        if (!updated) {
            return current;
        }

        long tsFlush = this.tsFlush;
        int slap = itimediff(current, tsFlush);
        if (slap >= 10000 || slap < -10000) {
            tsFlush = current;
            slap = 0;
        }

        if (slap >= 0) {
            return current;
        }

        int tmFlush = itimediff(tsFlush, current);
        int tmPacket = Integer.MAX_VALUE;

        for (Iterator<Segment> itr = sndBufItr.rewind(); itr.hasNext(); ) {
            Segment seg = itr.next();
            int diff = itimediff(seg.resendts, current);
            if (diff <= 0) {
                return current;
            }
            if (diff < tmPacket) {
                tmPacket = diff;
            }
        }

        int minimal = tmPacket < tmFlush ? tmPacket : tmFlush;
        if (minimal >= interval) {
            minimal = interval;
        }

        return current + minimal;
    }

    @Override
    public boolean checkFlush() {
        if (ackcount > 0) {
            return true;
        }
        if (probe != 0) {
            return true;
        }
        if (sndBuf.size() > 0) {
            return true;
        }
        if (sndQueue.size() > 0) {
            return true;
        }
        return false;
    }


    @Override
    public int setMtu(int mtu) {
        if (mtu < IKCP_OVERHEAD || mtu < 50) {
            return -1;
        }
        if (reserved >= mtu - IKCP_OVERHEAD || reserved < 0) {
            return -1;
        }

        this.mtu = mtu;
        this.mss = mtu - IKCP_OVERHEAD - reserved;
        return 0;
    }

    @Override
    public int getInterval() {
        return interval;
    }

    @Override
    public int nodelay(boolean nodelay, int interval, int resend, boolean nc) {
        this.nodelay = nodelay;
        if (nodelay) {
            this.rxMinrto = IKCP_RTO_NDL;
        } else {
            this.rxMinrto = IKCP_RTO_MIN;
        }

        if (interval >= 0) {
            if (interval > 5000) {
                interval = 5000;
            } else if (interval < 10) {
                interval = 10;
            }
            this.interval = interval;
        }

        if (resend >= 0) {
            fastresend = resend;
        }

        this.nocwnd = nc;

        return 0;
    }

    @Override
    public int waitSnd() {
        return this.sndBuf.size() + this.sndQueue.size();
    }

    @Override
    public long getConv() {
        return conv;
    }

    @Override
    public void setConv(long conv) {
        this.conv = conv;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public boolean isNodelay() {
        return nodelay;
    }

    @Override
    public void setNodelay(boolean nodelay) {
        this.nodelay = nodelay;
        if (nodelay) {
            this.rxMinrto = IKCP_RTO_NDL;
        } else {
            this.rxMinrto = IKCP_RTO_MIN;
        }
    }


    @Override
    public void setFastresend(int fastresend) {
        this.fastresend = fastresend;
    }


    @Override
    public void setRxMinrto(int rxMinrto) {
        this.rxMinrto = rxMinrto;
    }

    @Override
    public void setRcvWnd(int rcvWnd) {
        this.rcvWnd = rcvWnd;
    }

    @Override
    public void setAckMaskSize(int ackMaskSize) {
        this.ackMaskSize = ackMaskSize;
        this.IKCP_OVERHEAD += (ackMaskSize / 8);
        this.mss = mtu - IKCP_OVERHEAD - reserved;
    }

    @Override
    public void setReserved(int reserved) {
        this.reserved = reserved;
        this.mss = mtu - IKCP_OVERHEAD - reserved;
    }


    @Override
    public int getSndWnd() {
        return sndWnd;
    }

    @Override
    public void setSndWnd(int sndWnd) {
        this.sndWnd = sndWnd;
    }

    @Override
    public boolean isStream() {
        return stream;
    }

    @Override
    public void setStream(boolean stream) {
        this.stream = stream;
    }

    @Override
    public void setByteBufAllocator(ByteBufAllocator byteBufAllocator) {
        this.byteBufAllocator = byteBufAllocator;
    }

    @Override
    public KcpOutput getOutput() {
        return output;
    }

    @Override
    public void setOutput(KcpOutput output) {
        this.output = output;
    }


    @Override
    public void setAckNoDelay(boolean ackNoDelay) {
        this.ackNoDelay = ackNoDelay;
    }

    @Override
    public int getSrtt() {
        return rxSrtt;
    }

    @Override
    public String toString() {
        return "Kcp(" +
                "conv=" + conv +
                ')';
    }


    public static class Segment {


        private final Recycler.Handle<Kcp.Segment> recyclerHandle;
        /**
         * 会话id
         **/
        private long conv;
        /**
         * 命令
         **/
        private byte cmd;
        /**
         * message中的segment分片ID（在message中的索引，由大到小，0表示最后一个分片）
         **/
        private short frg;
        /**
         * 剩余接收窗口大小(接收窗口大小-接收队列大小)
         **/
        private int wnd;
        /**
         * message发送时刻的时间戳
         **/
        private long ts;
        /**
         * message分片segment的序号
         **/
        private long sn;
        /**
         * 待接收消息序号(接收滑动窗口左端)
         **/
        private long una;
        /**
         * 下次超时重传的时间戳
         **/
        private long resendts;
        /**
         * 该分片的超时重传等待时间
         **/
        private int rto;
        /**
         * 收到ack时计算的该分片被跳过的累计次数，即该分片后的包都被对方收到了，达到一定次数，重传当前分片
         **/
        private int fastack;
        /***发送分片的次数，每发送一次加一**/
        private int xmit;

        private long ackMask;

        private ByteBuf data;

        private int ackMaskSize;
        private static final Recycler<Kcp.Segment> RECYCLER = new Recycler<Kcp.Segment>() {

            @Override
            protected Segment newObject(Recycler.Handle<Segment> handle) {
                return new Segment(handle);
            }

        };

        private Segment(Recycler.Handle<Kcp.Segment> recyclerHandle) {
            this.recyclerHandle = recyclerHandle;
        }

        void recycle(boolean releaseBuf) {
            conv = 0;
            cmd = 0;
            frg = 0;
            wnd = 0;
            ts = 0;
            sn = 0;
            una = 0;
            resendts = 0;
            rto = 0;
            fastack = 0;
            xmit = 0;
            ackMask = 0;
            if (releaseBuf && data != null) {
                data.release();
            }
            data = null;

            recyclerHandle.recycle(this);
        }

        static Segment createSegment(ByteBufAllocator byteBufAllocator, int size) {
            Segment seg = RECYCLER.get();
            if (size == 0) {
                seg.data = null;
            } else {
                seg.data = byteBufAllocator.ioBuffer(size);
            }
            return seg;
        }

        public static Segment createSegment(ByteBuf buf) {
            Segment seg = RECYCLER.get();
            seg.data = buf;
            return seg;
        }


        public long getResendts() {
            return resendts;
        }

        public void setResendts(long resendts) {
            this.resendts = resendts;
        }

        public int getXmit() {
            return xmit;
        }

        public void setXmit(int xmit) {
            this.xmit = xmit;
        }
    }

}
