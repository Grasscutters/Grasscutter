package emu.grasscutter.game.trigger;

import java.util.EventListener;

public interface ITriggerListener extends EventListener {
    public boolean triggerEvent(TriggerEvent event);
}