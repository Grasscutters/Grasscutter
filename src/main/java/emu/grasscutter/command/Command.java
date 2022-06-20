package emu.grasscutter.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String label() default "";

    String usage() default "commands.generic.no_usage_specified";

    String description() default "commands.generic.no_description_specified";

    String[] aliases() default {};

    String permission() default "";

    String permissionTargeted() default "";

    enum TargetRequirement {
        NONE,       // targetPlayer is not required
        OFFLINE,    // targetPlayer must be offline
        PLAYER,     // targetPlayer can be online or offline
        ONLINE      // targetPlayer must be online
    }

    TargetRequirement targetRequirement() default TargetRequirement.ONLINE;

    boolean threading() default false;
}
