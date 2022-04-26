package emu.grasscutter.task;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/*
* So what is cron expression?
* second minute hour day month week year
*/
@Retention(RetentionPolicy.RUNTIME)
public @interface Task {
    String taskName() default "NO_NAME";
    String taskCronExpression() default "0 0 0 0 0 ?";
    String triggerName() default "NO_NAME";
}
