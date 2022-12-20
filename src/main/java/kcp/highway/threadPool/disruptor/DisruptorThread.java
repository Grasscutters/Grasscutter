package kcp.highway.threadPool.disruptor;

import kcp.highway.threadPool.IMessageExecutor;

/**
 * Created by JinMiao
 * 2018/5/2.
 */
public class DisruptorThread  extends Thread{
    /**
     * 线程所属线程池
     */
    private IMessageExecutor messageExecutor;


    public DisruptorThread(IMessageExecutor messageExecutor) {
        this.messageExecutor = messageExecutor;
    }

    public DisruptorThread(Runnable target, IMessageExecutor messageExecutor) {
        super(target);
        this.messageExecutor = messageExecutor;
    }

    public DisruptorThread(ThreadGroup group, Runnable target, IMessageExecutor messageExecutor) {
        super(group, target);
        this.messageExecutor = messageExecutor;
    }

    public DisruptorThread(String name, IMessageExecutor messageExecutor) {
        super(name);
        this.messageExecutor = messageExecutor;
    }

    public DisruptorThread(ThreadGroup group, String name, IMessageExecutor messageExecutor) {
        super(group, name);
        this.messageExecutor = messageExecutor;
    }

    public DisruptorThread(Runnable target, String name, IMessageExecutor messageExecutor) {
        super(target, name);
        this.messageExecutor = messageExecutor;
    }

    public DisruptorThread(ThreadGroup group, Runnable target, String name, IMessageExecutor messageExecutor) {
        super(group, target, name);
        this.messageExecutor = messageExecutor;
    }

    public DisruptorThread(ThreadGroup group, Runnable target, String name, long stackSize, IMessageExecutor messageExecutor) {
        super(group, target, name, stackSize);
        this.messageExecutor = messageExecutor;
    }

    public IMessageExecutor getMessageExecutor() {
        return messageExecutor;
    }

    public void setMessageExecutor(IMessageExecutor messageExecutor) {
        this.messageExecutor = messageExecutor;
    }

    @Override
    public String toString() {
        return "DisruptorThread{" +
                "messageExecutor=" + messageExecutor +
                '}';
    }
}
