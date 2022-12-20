package kcp.highway.threadPool.disruptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kcp.highway.threadPool.ITask;

public class DistriptorHandler
{

	protected static final Logger logger = LoggerFactory.getLogger(DistriptorHandler.class);
	private ITask task;


	public void execute()
	{
		try {
			this.task.execute();
			//得主动释放内存
			this.task = null;
		} catch (Throwable throwable) {
			logger.error("error",throwable);
		}
	}


	public void setTask(ITask task) {
		this.task = task;
	}
}
