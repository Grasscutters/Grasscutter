package emu.grasscutter.command;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String label() default "";

    String[] aliases() default {};

    String[] usage() default {""};

    String permission() default "";

    String permissionTargeted() default "";

    TargetRequirement targetRequirement() default TargetRequirement.ONLINE;

    boolean threading() default false;

    enum TargetRequirement {
        NONE, // targetPlayer is not required
        OFFLINE, // targetPlayer must be offline
        PLAYER, // targetPlayer can be online or offline
        ONLINE // targetPlayer must be online
    }
}
