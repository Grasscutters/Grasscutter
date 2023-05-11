package emu.grasscutter.game.ability;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import java.util.Map;
import lombok.AllArgsConstructor;

public final class AbilityLocalIdGenerator {
    public ConfigAbilitySubContainerType type;
    public long modifierIndex = 0;
    public long configIndex = 0;
    public long mixinIndex = 0;
    private long actionIndex = 0;

    public AbilityLocalIdGenerator(ConfigAbilitySubContainerType type) {
        this.type = type;
    }

    public void initializeActionLocalIds(
            AbilityModifierAction[] actions, Map<Integer, AbilityModifierAction> localIdToAction) {
        if (actions == null) return;

        actionIndex = 0;
        for (var action : actions) {
            actionIndex++;
            long id = GetLocalId();
            localIdToAction.put((int) id, action);
        }

        actionIndex = 0;
    }

    public long GetLocalId() {
        switch (type) {
            case ACTION -> {
                return type.value + (configIndex << 3) + (actionIndex << 9);
            }
            case MIXIN -> {
                return type.value + (mixinIndex << 3) + (configIndex << 9) + (actionIndex << 15);
            }
            case MODIFIER_ACTION -> {
                return type.value + (modifierIndex << 3) + (configIndex << 9) + (actionIndex << 15);
            }
            case MODIFIER_MIXIN -> {
                return type.value
                        + (modifierIndex << 3)
                        + (mixinIndex << 9)
                        + (configIndex << 15)
                        + (actionIndex << 21);
            }
            case NONE -> Grasscutter.getLogger().error("Ability local id generator using NONE type.");
        }

        return -1;
    }

    @AllArgsConstructor
    public enum ConfigAbilitySubContainerType {
        NONE(0),
        ACTION(1),
        MIXIN(2),
        MODIFIER_ACTION(3),
        MODIFIER_MIXIN(4);

        public final long value;
    }
}
