package emu.grasscutter.task;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/*
* So what is cron expression?
* Check this: https://en.wikipedia.org/wiki/Cron
# ┌───────────── minute (0 - 59)
# │ ┌───────────── hour (0 - 23)
# │ │ ┌───────────── day of the month (1 - 31)
# │ │ │ ┌───────────── month (1 - 12)
# │ │ │ │ ┌───────────── day of the week (0 - 6) (Sunday to Saturday;
# │ │ │ │ │                                   7 is also Sunday on some systems)
# │ │ │ │ │
# │ │ │ │ │
# * * * * *
* */
@Retention(RetentionPolicy.RUNTIME)
public @interface Task {
    String taskName() default "NO_NAME";
    String taskCronExpression() default "0 0 0 0 0 ?";
    String triggerName() default "NO_NAME";
}
