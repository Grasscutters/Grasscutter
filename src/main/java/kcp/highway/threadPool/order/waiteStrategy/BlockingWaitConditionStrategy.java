package kcp.highway.threadPool.order.waiteStrategy;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Disruptor默认的等待策略是BlockingWaitStrategy。
 * 这个策略的内部适用一个锁和条件变量来控制线程的执行和等待（Java基本的同步方法）。
 * BlockingWaitStrategy是最慢的等待策略，但也是CPU使用率最低和最稳定的选项。
 * 然而，可以根据不同的部署环境调整选项以提高性能。
 */
public final class BlockingWaitConditionStrategy implements WaitConditionStrategy {

    private final Lock lock;
    private final Condition processorNotifyCondition;

    public BlockingWaitConditionStrategy() {
        this(false);
    }

    public BlockingWaitConditionStrategy(boolean fairSync) {
        this.lock = new ReentrantLock(fairSync);
        this.processorNotifyCondition = this.lock.newCondition();
    }

    public void signalAllWhenBlocking() {
        this.lock.lock();
        try {
            this.processorNotifyCondition.signalAll();
            //this.processorNotifyCondition.signal();
        } finally {
            this.lock.unlock();
        }
    }


    @Override
    public <T> T waitFor(WaitCondition<T> waitCondition, long timeOut, TimeUnit unit) throws InterruptedException {
        this.lock.lock();
        try {
            long waitTime = unit.toNanos(timeOut);
            T task = waitCondition.getAttach();
            if(task==null)
            {
                this.processorNotifyCondition.awaitNanos(waitTime);
                task = waitCondition.getAttach();
            }
            return task;
        } finally {
            this.lock.unlock();
        }
    }
}
