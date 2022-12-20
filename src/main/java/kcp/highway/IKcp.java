package kcp.highway;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.Recycler;

import java.util.List;

/**
 * Created by JinMiao
 * 2020/7/20.
 */
public interface IKcp {
    void release();

    ByteBuf mergeRecv();

    /**
     * 1，判断是否有完整的包，如果有就抛给下一层
     * 2，整理消息接收队列，判断下一个包是否已经收到 收到放入rcvQueue
     * 3，判断接收窗口剩余是否改变，如果改变记录需要通知
     * @param bufList
     * @return
     */
    int recv(List<ByteBuf> bufList);

    /**
     * check the size of next message in the recv queue
     * 检查接收队列里面是否有完整的一个包，如果有返回该包的字节长度
     * @return -1 没有完整包， >0 一个完整包所含字节
     */
    int peekSize();

    /**
     * 判断一条消息是否完整收全了
     * @return
     */
    boolean canRecv();

    int send(ByteBuf buf);

    int input(ByteBuf data, boolean regular, long current);

    long currentMs(long now);

    /**
     * ikcp_flush
     */
    long flush(boolean ackOnly, long current);

    /**
     * update getState (call it repeatedly, every 10ms-100ms), or you can ask
     * ikcp_check when to call it again (without ikcp_input/_send calling).
     * 'current' - current timestamp in millisec.
     *
     * @param current
     */
    void update(long current);

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
    long check(long current);

    boolean checkFlush();

    int setMtu(int mtu);

    int getInterval();

    int nodelay(boolean nodelay, int interval, int resend, boolean nc);

    int waitSnd();

    long getConv();

    void setConv(long conv);

    User getUser();

    void setUser(User user);

    int getState();

    void setState(int state);

    boolean isNodelay();

    void setNodelay(boolean nodelay);


    void setFastresend(int fastresend);


    void setRxMinrto(int rxMinrto);


    void setRcvWnd(int rcvWnd);

    void setAckMaskSize(int ackMaskSize);

    void setReserved(int reserved);

    int getSndWnd();

    void setSndWnd(int sndWnd);

    boolean isStream();

    void setStream(boolean stream);

    void setByteBufAllocator(ByteBufAllocator byteBufAllocator);

    KcpOutput getOutput();

    void setOutput(KcpOutput output);

    void setAckNoDelay(boolean ackNoDelay);

    int getSrtt();
}
