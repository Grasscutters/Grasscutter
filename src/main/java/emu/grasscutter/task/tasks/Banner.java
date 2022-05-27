package emu.grasscutter.task.tasks;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.task.Task;
import emu.grasscutter.task.TaskHandler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Task(taskName = "Banner", taskCronExpression = "0 0/5 * * * ?", triggerName = "BannerTrigger")
public final class Banner extends TaskHandler {

    @Override
    public void onEnable() {
        Grasscutter.getLogger().info("[Task] Banner task enabled.");
    }

    @Override
    public void onDisable() {
        Grasscutter.getLogger().info("[Task] Banner task disabled.");
    }

    @Override
    public synchronized void execute(JobExecutionContext context) throws JobExecutionException {
        Grasscutter.getGameServer().getGachaManager().update(context.getNextFireTime().getTime()/1000);
    }
}