package emu.grasscutter.server.scheduler;

import emu.grasscutter.Grasscutter;
import lombok.*;

/**
 * This class works the same as a runnable, except with more information.
 */
public final class ServerTask implements Runnable {
    /* The runnable to run. */
    private final Runnable runnable;
    /* This ID is assigned by the scheduler. */
    @Getter private final int taskId;
    /* The period at which the task should be run. */
    /* The delay between the first execute. */
    private final int period, delay;

    public ServerTask(Runnable runnable, int taskId, int period, int delay) {
        this.runnable = runnable;
        this.taskId = taskId;
        this.period = period;
        this.delay = delay;
    }

    /* The amount of times the task has been run. */
    @Getter private int ticks = 0;
    /* Should the check consider delay? */
    private boolean considerDelay = true;

    /**
     * Cancels the task from running the next time.
     */
    public void cancel() {
        Grasscutter.getGameServer().getScheduler().cancelTask(this.taskId);
    }

    /**
     * Checks if the task should run at the current tick.
     * @return True if the task should run, false otherwise.
     */
    public boolean shouldRun() {
        if(this.delay != -1 && this.considerDelay) {
            this.considerDelay = false;
            return this.ticks == this.delay;
        } else if(this.period != -1)
            return this.ticks % this.period == 0;
        else return true;
    }

    /**
     * Checks if the task should be canceled.
     * @return True if the task should be canceled, false otherwise.
     */
    public boolean shouldCancel() {
        return this.period == -1;
    }

    /**
     * Runs the task.
     */
    @Override public void run() {
        // Run the runnable.
        this.runnable.run();
        // Increase tick count.
        this.ticks++;
    }
}