package emu.grasscutter.data.binout;

import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;

import java.util.ArrayList;
import java.util.List;

public class AbilityModifierEntry {
    private final String name; // Custom value
    public List<AbilityModifierAction> onModifierAdded;
    public List<AbilityModifierAction> onThinkInterval;
    public List<AbilityModifierAction> onRemoved;

    public AbilityModifierEntry(String name) {
        this.name = name;
        this.onModifierAdded = new ArrayList<>();
        this.onThinkInterval = new ArrayList<>();
        this.onRemoved = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public List<AbilityModifierAction> getOnAdded() {
        return this.onModifierAdded;
    }

    public List<AbilityModifierAction> getOnThinkInterval() {
        return this.onThinkInterval;
    }

    public List<AbilityModifierAction> getOnRemoved() {
        return this.onRemoved;
    }

}
