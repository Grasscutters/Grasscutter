package emu.grasscutter.task;

import org.quartz.Job;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
public abstract class TaskHandler implements Job {
    public void restartExecute() throws JobExecutionException {
        this.execute(null);
    }

    public abstract void onEnable();

    public abstract void onDisable();
}
