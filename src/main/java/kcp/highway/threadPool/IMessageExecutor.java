package kcp.highway.threadPool;

/**
 * 消息处理器
 */
public interface IMessageExecutor{
	/**
	 * 停止消息处理器
	 */
	void stop();



	/**
	 * 判断队列是否已经达到上限了
	 * @return
	 */
	boolean isFull();


	/**
	 * 执行任务
	 * 注意: 如果线程等于当前线程 则直接执行  如果非当前线程放进队列
	 *
	 * @param iTask
	 */
	void execute(ITask iTask);
}