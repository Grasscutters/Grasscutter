package emu.grasscutter.task;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.Account;
import emu.grasscutter.game.Player;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.MutableTrigger;
import org.reflections.Reflections;

import java.util.*;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public final class TaskMap {
    private final Map<String, TaskHandler> tasks = new HashMap<>();
    private final Map<String, Task> annotations = new HashMap<>();
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

    public TaskMap registerTask(String taskName, TaskHandler task) {
        Task annotation = task.getClass().getAnnotation(Task.class);
        this.annotations.put(taskName, annotation);
        this.tasks.put(taskName, task);

        // register task
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDetail job = JobBuilder
                        .newJob(task.getClass())
                        .withIdentity(taskName)
                        .build();
            
            Trigger convTrigger = TriggerBuilder.newTrigger()
                        .withIdentity(annotation.triggerName())
                        .withSchedule(CronScheduleBuilder.cronSchedule(annotation.taskCronExpression()))
                        .build();
            
            scheduler.scheduleJob(job, convTrigger);
            scheduler.start();
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
                Object object = annotated.newInstance();
                if (object instanceof TaskHandler)
                    this.registerTask(taskData.taskName(), (TaskHandler) object);
                else Grasscutter.getLogger().error("Class " + annotated.getName() + " is not a TaskHandler!");
            } catch (Exception exception) {
                Grasscutter.getLogger().error("Failed to register task handler for " + annotated.getSimpleName(), exception);
            }
        });
    }
}
