package emu.grasscutter.task;

import org.quartz.*;

@PersistJobDataAfterExecution
public class TaskHandler implements Job {

    public void restartExecute() throws JobExecutionException {
        execute(null);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // TODO Auto-generated method stub

    }

}
