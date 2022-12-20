package kcp.highway.threadPool;

/**
 * Created by JinMiao
 * 2020/11/24.
 */
public interface IMessageExecutorPool {
    /**
     * 从线程池中按算法获得一个线程对象
     * @return
     */
    IMessageExecutor getIMessageExecutor();

    void stop();

}
