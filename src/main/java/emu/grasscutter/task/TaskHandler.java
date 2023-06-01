package emu.grasscutter.task;

import org.quartz.*;

@PersistJobDataAfterExecution
public abstract class TaskHandler implements Job {
    public void restartExecute() throws JobExecutionException {
        execute(null);
    }

    public abstract void onEnable();

    public abstract void onDisable();
}
