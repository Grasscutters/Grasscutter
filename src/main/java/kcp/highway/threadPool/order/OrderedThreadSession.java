package kcp.highway.threadPool.order;

import org.jctools.queues.MpscLinkedQueue;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by JinMiao
 * 2020/6/19.
 */
public class OrderedThreadSession {

    private int id;

    private Queue<Runnable> queue;

    /**
     * The current task state
     */
    private final AtomicBoolean processingCompleted = new AtomicBoolean(true);

    //每次执行任务最大数量
    private int runTaskCount = 0;

    public OrderedThreadSession() {
        this(new MpscLinkedQueue<>());
    }

    public OrderedThreadSession(Queue<Runnable> queue) {
        this(queue, Integer.MAX_VALUE);
    }

    public OrderedThreadSession(Queue<Runnable> queue, int runTaskCount) {
        this.queue = queue;
        this.runTaskCount = runTaskCount;
    }

    public void setQueue(Queue<Runnable> queue) {
        this.queue = queue;
    }

    public Queue<Runnable> getQueue() {
        return queue;
    }

    public AtomicBoolean getProcessingCompleted() {
        return processingCompleted;
    }

    public int getRunTaskCount() {
        return runTaskCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRunTaskCount(int runTaskCount) {
        this.runTaskCount = runTaskCount;
    }
}
