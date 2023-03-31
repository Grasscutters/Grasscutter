package emu.grasscutter.task;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/*
* So what is cron expression?
The format of a Cron expression is as follows.
Second Minute Hour Day Month Week Year
Seconds: 0-59
Minute: 0-59
hour: 0-23
Day: 1-31
Month: 1-12
Week: 1-7 (0-6 sometimes)
Year: Specify your own

If you want to express every second or every minute or something like that, use the * symbol in that position;
if you want to express more than one such as every 15 minutes and every 30 minutes, you can write:`15, 30`.

For the rest of the wildcard characters, please Google them yourself
*/

@Retention(RetentionPolicy.RUNTIME)
public @interface Task {
    String taskName() default "NO_NAME";

    String taskCronExpression() default "0 0 0 0 0 ?";

    String triggerName() default "NO_NAME";

    boolean executeImmediatelyAfterReset() default false;

    boolean executeImmediately() default false;
}
