package emu.grasscutter.game.activity;

import emu.grasscutter.game.props.ActivityType;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GameActivity {
    ActivityType value();
}
