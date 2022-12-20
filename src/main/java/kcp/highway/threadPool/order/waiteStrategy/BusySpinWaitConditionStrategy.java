package kcp.highway.threadPool.order.waiteStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * BusySpinWaitStrategy是性能最高的等待策略，
 * 同时也是对部署环境要求最高的策略。
 * 这个性能最好用在事件处理线程比物理内核数目还要小的时候。
 * 例如：在禁用超线程技术的时候。
 */
public final class BusySpinWaitConditionStrategy implements WaitConditionStrategy
{
	protected final Logger log= LoggerFactory.getLogger(getClass());
	
    @Override
	public <T> T waitFor(WaitCondition<T> waitCondition, long timeOut, TimeUnit unit) {
		long endTime=System.nanoTime()+unit.toNanos(timeOut);
		T task;
    	while((task=waitCondition.getAttach())==null)
		{
    		if(System.nanoTime()>=endTime)
    		{
    			break;
    		}
		}
		return task;
	}

    @Override
    public void signalAllWhenBlocking()
    {
    	
    }
}
