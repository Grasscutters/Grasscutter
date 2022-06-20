package emu.grasscutter.task;

import emu.grasscutter.Grasscutter;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.reflections.Reflections;

import java.util.*;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public final class TaskMap {
    private final Map<String, TaskHandler> tasks = new HashMap<>();
    private final Map<String, Task> annotations = new HashMap<>();
    private final Map<String, TaskHandler> afterReset = new HashMap<>();
    private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    public TaskMap() {
        this(false);
    }

    public TaskMap(boolean scan) {
        if (scan) this.scan();
    }

    public static TaskMap getInstance() {
        return Grasscutter.getGameServer().getTaskMap();
    }

    public void resetNow() {
        // Unregister all tasks
        for (TaskHandler task : this.tasks.values()) {
            this.unregisterTask(task);
        }

        // Run all afterReset tasks
        for (TaskHandler task : this.afterReset.values()) {
            try {
                task.restartExecute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Remove all afterReset tasks
        this.afterReset.clear();

        // Register all tasks
        for (TaskHandler task : this.tasks.values()) {
            this.registerTask(task.getClass().getAnnotation(Task.class).taskName(), task);
        }
    }

    public TaskMap unregisterTask(TaskHandler task) {
        this.tasks.remove(task.getClass().getAnnotation(Task.class).taskName());
        this.annotations.remove(task.getClass().getAnnotation(Task.class).taskName());

        try {
            Scheduler scheduler = this.schedulerFactory.getScheduler();
            scheduler.deleteJob(new JobKey(task.getClass().getAnnotation(Task.class).taskName()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        task.onDisable();

        return this;
    }

    public boolean pauseTask(String taskName) {
        try {
            Scheduler scheduler = this.schedulerFactory.getScheduler();
            scheduler.pauseJob(new JobKey(taskName));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean resumeTask(String taskName) {
        try {
            Scheduler scheduler = this.schedulerFactory.getScheduler();
            scheduler.resumeJob(new JobKey(taskName));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean cancelTask(String taskName) {
        Task task = this.annotations.get(taskName);
        if (task == null) return false;
        try {
            this.unregisterTask(this.tasks.get(taskName));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public TaskMap registerTask(String taskName, TaskHandler task) {
        Task annotation = task.getClass().getAnnotation(Task.class);
        this.annotations.put(taskName, annotation);
        this.tasks.put(taskName, task);

        // register task
        try {
            Scheduler scheduler = this.schedulerFactory.getScheduler();
            JobDetail job = JobBuilder
                .newJob(task.getClass())
                .withIdentity(taskName)
                .build();

            Trigger convTrigger = TriggerBuilder.newTrigger()
                .withIdentity(annotation.triggerName())
                .withSchedule(CronScheduleBuilder.cronSchedule(annotation.taskCronExpression()))
                .build();

            scheduler.scheduleJob(job, convTrigger);

            if (annotation.executeImmediately()) {
                task.execute(null);
            }
            task.onEnable();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return this;
    }

    public List<TaskHandler> getHandlersAsList() {
        return new LinkedList<>(this.tasks.values());
    }

    public HashMap<String, TaskHandler> getHandlers() {
        return new LinkedHashMap<>(this.tasks);
    }

    public TaskHandler getHandler(String taskName) {
        return this.tasks.get(taskName);
    }

    private void scan() {
        Reflections reflector = Grasscutter.reflector;
        Set<Class<?>> classes = reflector.getTypesAnnotatedWith(Task.class);
        classes.forEach(annotated -> {
            try {
                Task taskData = annotated.getAnnotation(Task.class);
                Object object = annotated.getDeclaredConstructor().newInstance();
                if (object instanceof TaskHandler) {
                    this.registerTask(taskData.taskName(), (TaskHandler) object);
                    if (taskData.executeImmediatelyAfterReset()) {
                        this.afterReset.put(taskData.taskName(), (TaskHandler) object);
                    }
                } else {
                    Grasscutter.getLogger().error("Class " + annotated.getName() + " is not a TaskHandler!");
                }
            } catch (Exception exception) {
                Grasscutter.getLogger().error("Failed to register task handler for " + annotated.getSimpleName(), exception);
            }
        });
        try {
            Scheduler scheduler = this.schedulerFactory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
