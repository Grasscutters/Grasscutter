package emu.grasscutter.game.activity;

import emu.grasscutter.game.props.WatcherTriggerType;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ActivityWatcherType {
    WatcherTriggerType value();
}
