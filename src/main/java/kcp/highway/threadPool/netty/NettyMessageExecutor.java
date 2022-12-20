package kcp.highway.threadPool.netty;

import io.netty.channel.EventLoop;
import kcp.highway.threadPool.IMessageExecutor;
import kcp.highway.threadPool.ITask;

/**
 * Created by JinMiao
 * 2020/11/24.
 */
public class NettyMessageExecutor implements IMessageExecutor {

    private EventLoop eventLoop;


    public NettyMessageExecutor(EventLoop eventLoop) {
        this.eventLoop = eventLoop;
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public void execute(ITask iTask) {
        //if(eventLoop.inEventLoop()){
        //    iTask.execute();
        //}else{
            this.eventLoop.execute(() -> iTask.execute());
        //}
    }
}
