package emu.grasscutter.server.scheduler;

import emu.grasscutter.Grasscutter;
import lombok.Getter;

/** This class works the same as a runnable, except with more information. */
public final class ServerTask implements Runnable {
    /* The runnable to run. */
    private final Runnable runnable;
    /* This ID is assigned by the scheduler. */
    @Getter private final int taskId;
    /* The period at which the task should be run. */
    /* The delay between the first execute. */
    private final int period, delay;
    /* The amount of times the task has been run. */
    @Getter private int ticks = 0;
    /* Should the check consider delay? */
    private boolean considerDelay = true;

    public ServerTask(Runnable runnable, int taskId, int period, int delay) {
        this.runnable = runnable;
        this.taskId = taskId;
        this.period = period;
        this.delay = delay;
    }

    /** Cancels the task from running the next time. */
    public void cancel() {
        Grasscutter.getGameServer().getScheduler().cancelTask(this.taskId);
    }

    /**
     * Checks if the task should run at the current tick.
     *
     * @return True if the task should run, false otherwise.
     */
    public boolean shouldRun() {
        // Increase tick count.
        ++this.ticks;
        if (this.delay != -1 && this.considerDelay) {
            // Check if the task should run.
            var shouldRun = ticks >= this.delay;
            // Check if the task should be canceled.
            if (shouldRun) this.considerDelay = false;

            return shouldRun; // Return the result.
        } else if (this.period != -1) return ticks % this.period == 0;
        else return true;
    }

    /**
     * Checks if the task should be canceled.
     *
     * @return True if the task should be canceled, false otherwise.
     */
    public boolean shouldCancel() {
        return this.period == -1 && ticks >= delay;
    }

    /** Runs the task. */
    @Override
    public void run() {
        // Run the runnable.
        try {
            this.runnable.run();
        } catch (Exception ex) {
            Grasscutter.getLogger().error("Exception during task: ", ex);
        }
    }
}
