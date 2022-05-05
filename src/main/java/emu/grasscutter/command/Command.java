package emu.grasscutter.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String label() default "";

    String usage() default "No usage specified";

    String description() default "No description specified";

    String[] aliases() default {};

    String permission() default "";
    
    String permissionTargeted() default "";

    boolean threading() default false;
}
