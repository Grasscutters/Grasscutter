package emu.grasscutter.server.scheduler;

import javax.annotation.Nullable;
import lombok.Getter;

/** A server task that should be run asynchronously. */
public final class AsyncServerTask implements Runnable {
    /* The runnable to run. */
    private final Runnable task;
    /* This ID is assigned by the scheduler. */
    @Getter private final int taskId;
    /* The result callback to run. */
    @Nullable private final Runnable callback;

    /* Has the task already been started? */
    private boolean started = false;
    /* Has the task finished execution? */
    private boolean finished = false;
    /* The result produced in the async task. */
    @Nullable private Object result = null;

    /**
     * For tasks without a callback.
     *
     * @param task The task to run.
     */
    public AsyncServerTask(Runnable task, int taskId) {
        this(task, null, taskId);
    }

    /**
     * For tasks with a callback.
     *
     * @param task The task to run.
     * @param callback The task to run after the task is complete.
     */
    public AsyncServerTask(Runnable task, @Nullable Runnable callback, int taskId) {
        this.task = task;
        this.callback = callback;
        this.taskId = taskId;
    }

    /**
     * Returns the state of the task.
     *
     * @return True if the task has been started, false otherwise.
     */
    public boolean hasStarted() {
        return this.started;
    }

    /**
     * Returns the state of the task.
     *
     * @return True if the task has finished execution, false otherwise.
     */
    public boolean isFinished() {
        return this.finished;
    }

    /** Runs the task. */
    @Override
    public void run() {
        // Declare the task as started.
        this.started = true;

        // Run the runnable.
        this.task.run();

        // Declare the task as finished.
        this.finished = true;
    }

    /** Runs the callback. */
    public void complete() {
        // Run the callback.
        if (this.callback != null) this.callback.run();
    }

    /**
     * Returns the set result of the async task.
     *
     * @return The result, or null if it has not been set.
     */
    @Nullable public Object getResult() {
        return this.result;
    }

    /**
     * Sets the result of the async task.
     *
     * @param result The result of the async task.
     */
    public void setResult(@Nullable Object result) {
        this.result = result;
    }
}
