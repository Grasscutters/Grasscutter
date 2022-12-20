package kcp.highway.threadPool.order.waiteStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * SleepingWaitStrategy和BlockingWaitStrategy一样, SpleepingWaitStrategy的CPU使用率也比较低。
 * 它的方式是循环等待并且在循环中间调用LockSupport.parkNanos(1)来睡眠，（在Linux系统上面睡眠时间60µs）.
 * 然而，它的优点在于生产线程只需要计数，而不执行任何指令。并且没有条件变量的消耗。
 * 但是，事件对象从生产者到消费者传递的延迟变大了。SleepingWaitStrategy最好用在不需要低延迟，
 * 而且事件发布对于生产者的影响比较小的情况下。比如异步日志功能。
 */
public final class SleepingWaitConditionStrategy implements WaitConditionStrategy
{
	protected final Logger log= LoggerFactory.getLogger(getClass());
    private static final int DEFAULT_RETRIES = 200;

    private final int retries;

    public SleepingWaitConditionStrategy()
    {
        this(DEFAULT_RETRIES);
    }

    public SleepingWaitConditionStrategy(int retries)
    {
        this.retries = retries;
    }
    

    @Override
	public <T> T waitFor(WaitCondition<T> waitCondition, long timeOut, TimeUnit unit) {
		int counter = retries;
		long endTime=System.nanoTime()+unit.toNanos(timeOut);
		T task;
		while ((task=waitCondition.getAttach())==null)
	    {
			if(System.nanoTime()>=endTime)
			{
				break;
			}
	        counter = applyWaitMethod(counter);
	    }
	    return task;
	}

	@Override
    public void signalAllWhenBlocking()
    {
    }
    
    private int applyWaitMethod(int counter)
    {
    	if (counter > 100) {
			--counter;
		} else if (counter > 0) {
			--counter;
			Thread.yield();
		} else {
			LockSupport.parkNanos(1L);
		}
		return counter;
    }
}
