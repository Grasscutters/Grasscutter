package emu.grasscutter.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface TaskHandler extends Job {
    default void execute(JobExecutionContext context) throws JobExecutionException {
        
    }
}
