package emu.grasscutter.data.binout;

import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import java.util.*;
import lombok.Getter;

public class AbilityModifierEntry {
    public final List<AbilityModifierAction> onModifierAdded;
    @Getter public final List<AbilityModifierAction> onThinkInterval;
    @Getter public final List<AbilityModifierAction> onRemoved;
    @Getter private final String name; // Custom value

    public AbilityModifierEntry(String name) {
        this.name = name;
        this.onModifierAdded = new ArrayList<>();
        this.onThinkInterval = new ArrayList<>();
        this.onRemoved = new ArrayList<>();
    }

    public List<AbilityModifierAction> getOnAdded() {
        return onModifierAdded;
    }
}
