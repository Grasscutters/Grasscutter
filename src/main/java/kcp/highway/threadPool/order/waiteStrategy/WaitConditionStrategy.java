package kcp.highway.threadPool.order.waiteStrategy;

import java.util.concurrent.TimeUnit;

public interface WaitConditionStrategy
{
	
    /**
          * 等待一个条件,如果条件成立返回结果,如果超过时间,不管成立与否都返回结果
     * @param waitCondition
     * @param timeOut
     * @param unit
     * @return
     * @throws InterruptedException
     */
    <T> T waitFor(WaitCondition<T> waitCondition,long timeOut,TimeUnit unit) throws InterruptedException;
    
    /**
     * 释放解除阻塞信号
     */
    void signalAllWhenBlocking();
}
