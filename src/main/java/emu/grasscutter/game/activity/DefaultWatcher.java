package emu.grasscutter.game.activity;

import emu.grasscutter.game.props.WatcherTriggerType;

@ActivityWatcherType(WatcherTriggerType.TRIGGER_NONE)
public class DefaultWatcher extends ActivityWatcher{
    @Override
    protected boolean isMeet(String... param) {
        return false;
    }
}
