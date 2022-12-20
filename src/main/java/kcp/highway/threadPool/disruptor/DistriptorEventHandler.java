package kcp.highway.threadPool.disruptor;

import com.lmax.disruptor.EventHandler;

public class DistriptorEventHandler implements EventHandler<DistriptorHandler>{

	@Override
    public void onEvent(DistriptorHandler event, long sequence,
                        boolean endOfBatch) {
		event.execute();
	}
}
