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
            AbilityModifierAction actions[], Map<Integer, AbilityModifierAction> localIdToAction) {
        this.initializeActionLocalIds(actions, localIdToAction, false);
    }

    public void initializeActionLocalIds(
            AbilityModifierAction[] actions,
            Map<Integer, AbilityModifierAction> localIdToAction,
            boolean preserveActionIndex) {
        if (actions == null) return;
        if (!preserveActionIndex) this.actionIndex = 0;
        for (int i = 0; i < actions.length; i++) {
            this.actionIndex++;

            var id = GetLocalId();
            localIdToAction.put((int) id, actions[i]);

            if (actions[i].actions != null)
                this.initializeActionLocalIds(actions[i].actions, localIdToAction, true);
            else {
                if (actions[i].successActions != null)
                    this.initializeActionLocalIds(
                            actions[i].successActions,
                            localIdToAction,
                            true); // Need to check this specific order
                if (actions[i].failActions != null)
                    this.initializeActionLocalIds(actions[i].failActions, localIdToAction, true);
            }
        }

        if (!preserveActionIndex) actionIndex = 0;
    }

    public void initializeMixinsLocalIds(
            AbilityMixinData[] mixins, Map<Integer, AbilityMixinData> localIdToAction) {
        if (mixins == null) return;
        this.mixinIndex = 0;
        for (var mixin : mixins) {
            var id = GetLocalId();
            localIdToAction.put((int) id, mixin);

            this.mixinIndex++;
        }

        this.mixinIndex = 0;
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
