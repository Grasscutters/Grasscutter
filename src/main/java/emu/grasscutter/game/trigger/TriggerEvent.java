package emu.grasscutter.game.trigger;

import emu.grasscutter.game.trigger.enums.Trigger;

import java.util.EventObject;

public class TriggerEvent extends EventObject implements Cloneable {
    private Trigger triggerType;
    private int id;

    public TriggerEvent(Object source, Trigger triggerType, int id) {
        super(source);
        this.triggerType = triggerType;
        this.id = id;
    }

    public Trigger getTriggerType() {
        return triggerType;
    }

    public int getId() {
        return id;
    }
}
