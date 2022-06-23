package emu.grasscutter.server.scheduler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * A class to manage all time-based tasks scheduled on the server.
 * This handles both synchronous and asynchronous tasks.
 *
 * Developers note: A server tick is ONE REAL-TIME SECOND.
 */
public final class ServerTaskScheduler {
    /* A map to contain all running tasks. */
    private final ConcurrentHashMap<Integer, ServerTask> tasks
        = new ConcurrentHashMap<>();
    /* A map to contain all async tasks. */
    private final ConcurrentHashMap<Integer, AsyncServerTask> asyncTasks
        = new ConcurrentHashMap<>();

    /* The ID assigned to the next runnable. */
    private int nextTaskId = 0;

    /**
     * Ran every server tick.
     * Attempts to run all scheduled tasks.
     * This method is synchronous and will block until all tasks are complete.
     */
    public void runTasks() {
        // Skip if there are no tasks.
        if(this.tasks.size() == 0)
            return;

        // Run all tasks.
        for(ServerTask task : this.tasks.values()) {
            // Check if the task should run.
            if (task.shouldRun()) {
                // Run the task.
                task.run();
            }

            // Check if the task should be canceled.
            if (task.shouldCancel()) {
                // Cancel the task.
                this.cancelTask(task.getTaskId());
            }
        }

        // Run all async tasks.
        for(AsyncServerTask task : this.asyncTasks.values()) {
            if(!task.hasStarted()) {
                // Create a thread for the task.
                Thread thread = new Thread(task);
                // Start the thread.
                thread.start();
            } else if(task.isFinished()) {
                // Cancel the task.
                this.asyncTasks.remove(task.getTaskId());
                // Run the task's callback.
                task.complete();
            }
        }
    }

    /**
     * Gets a task from the scheduler.
     * @param taskId The ID of the task to get.
     * @return The task, or null if it does not exist.
     */
    public ServerTask getTask(int taskId) {
        return this.tasks.get(taskId);
    }

    /**
     * Gets an async task from the scheduler.
     * @param taskId The ID of the task to get.
     * @return The task, or null if it does not exist.
     */
    public AsyncServerTask getAsyncTask(int taskId) {
        return this.asyncTasks.get(taskId);
    }

    /**
     * Removes a task from the scheduler.
     * @param taskId The ID of the task to remove.
     */
    public void cancelTask(int taskId) {
        this.tasks.remove(taskId);
    }

    /**
     * Schedules a task to be run on a separate thread.
     * The task runs on the next server tick.
     * @param runnable The runnable to run.
     * @return The ID of the task.
     */
    public int scheduleAsyncTask(Runnable runnable) {
        // Get the next task ID.
        var taskId = this.nextTaskId++;
        // Create a new task.
        this.asyncTasks.put(taskId, new AsyncServerTask(runnable, taskId));
        // Return the task ID.
        return taskId;
    }

    /**
     * Schedules a task to be run on the next server tick.
     * @param runnable The runnable to run.
     * @return The ID of the task.
     */
    public int scheduleTask(Runnable runnable) {
        return this.scheduleDelayedRepeatingTask(runnable, -1, -1);
    }

    /**
     * Schedules a task to be run after the amount of ticks has passed.
     * @param runnable The runnable to run.
     * @param delay The amount of ticks to wait before running.
     * @return The ID of the task.
     */
    public int scheduleDelayedTask(Runnable runnable, int delay) {
        return this.scheduleDelayedRepeatingTask(runnable, -1, delay);
    }

    /**
     * Schedules a task to be run every amount of ticks.
     * @param runnable The runnable to run.
     * @param period The amount of ticks to wait before running again.
     * @return The ID of the task.
     */
    public int scheduleRepeatingTask(Runnable runnable, int period) {
        return this.scheduleDelayedRepeatingTask(runnable, period, 0);
    }

    /**
     * Schedules a task to be run after the amount of ticks has passed.
     * @param runnable The runnable to run.
     * @param period The amount of ticks to wait before running again.
     * @param delay The amount of ticks to wait before running the first time.
     * @return The ID of the task.
     */
    public int scheduleDelayedRepeatingTask(Runnable runnable, int period, int delay) {
        // Get the next task ID.
        var taskId = this.nextTaskId++;
        // Create a new task.
        this.tasks.put(taskId, new ServerTask(runnable, taskId, period, delay));
        // Return the task ID.
        return taskId;
    }
}