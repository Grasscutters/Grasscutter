package emu.grasscutter.game.ability;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityMixinData;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import java.util.Map;
import lombok.AllArgsConstructor;

@SuppressWarnings("ALL")
public class AbilityLocalIdGenerator {
    @AllArgsConstructor
    public enum ConfigAbilitySubContainerType {
        NONE(0),
        ACTION(1),
        MIXIN(2),
        MODIFIER_ACTION(3),
        MODIFIER_MIXIN(4);

        final long value;
    }

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
        for (AbilityModifierAction action : actions) {
            actionIndex++;
            long id = GetLocalId();
            localIdToAction.put((int) id, action);
        }

        actionIndex = 0;
    }

    public void initializeMixinsLocalIds(
            AbilityMixinData[] mixins, Map<Integer, AbilityMixinData> localIdToAction) {
        if (mixins == null) return;
        mixinIndex = 0;
        for (AbilityMixinData mixin : mixins) {
            long id = GetLocalId();
            localIdToAction.put((int) id, mixin);

            mixinIndex++;
        }

        mixinIndex = 0;
    }

    public long GetLocalId() {
        switch (type) {
            case ACTION -> {
                return (long) type.value + (configIndex << 3) + (actionIndex << 9);
            }
            case MIXIN -> {
                return (long) type.value + (mixinIndex << 3) + (configIndex << 9) + (actionIndex << 15);
            }
            case MODIFIER_ACTION -> {
                return (long) type.value + (modifierIndex << 3) + (configIndex << 9) + (actionIndex << 15);
            }
            case MODIFIER_MIXIN -> {
                return (long) type.value
                        + (modifierIndex << 3)
                        + (mixinIndex << 9)
                        + (configIndex << 15)
                        + (actionIndex << 21);
            }
            case NONE -> Grasscutter.getLogger().error("Ability local id generator using NONE type.");
        }

        return -1;
    }
}
