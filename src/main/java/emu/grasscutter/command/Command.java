package emu.grasscutter.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

enum TargetRequirement {
    NONE,       // targetPlayer is not required
    OFFLINE,    // targetPlayer must be offline
    PLAYER,     // targetPlayer can be online or offline
    ONLINE      // targetPlayer must be online
}

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String label() default "";

    String usage() default "No usage specified";

    String description() default "commands.generic.no_description_specified";

    String[] aliases() default {};

    String permission() default "";
    
    String permissionTargeted() default "";

    TargetRequirement targetRequirement() default TargetRequirement.ONLINE;

    boolean threading() default false;
}
