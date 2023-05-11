package emu.grasscutter.data.binout;

import static emu.grasscutter.game.ability.AbilityLocalIdGenerator.*;

import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.AbilityLocalIdGenerator;
import java.util.HashMap;
import java.util.Map;

public class AbilityData {
    public String abilityName;
    public Map<String, AbilityModifier> modifiers;
    public boolean isDynamicAbility;
    public Map<String, Float> abilitySpecials;
    public AbilityModifierAction[] onAdded;

    // abilityMixins
    // onAbilityStart
    // onKill

    public final Map<Integer, AbilityModifierAction> localIdToAction = new HashMap<>();

    private boolean _initialized = false;

    public void initialize() {
        if (_initialized) return;
        _initialized = true;

        if (modifiers == null) return;

        var _modifiers = modifiers.values().toArray(new AbilityModifier[0]);
        var modifierIndex = 0;
        for (var modifier : _modifiers) {
            long configIndex = 0L;
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onAdded, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onRemoved, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onBeingHit, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onAttackLanded, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onHittingOther, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onThinkInterval, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onKill, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onCrash, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onAvatarIn, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onAvatarOut, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onReconnect, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onChangeAuthority, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onVehicleIn, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onVehicleOut, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onZoneEnter, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onZoneExit, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onHeal, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, modifier.onBeingHealed, localIdToAction);

            modifierIndex++;
        }
    }

    private void initializeActionSubCategory(
            long modifierIndex,
            long configIndex,
            AbilityModifierAction[] actions,
            Map<Integer, AbilityModifierAction> localIdToAction) {
        if (actions == null) return;

        var generator = new AbilityLocalIdGenerator(ConfigAbilitySubContainerType.MODIFIER_ACTION);
        generator.modifierIndex = modifierIndex;
        generator.configIndex = configIndex;

        generator.initializeActionLocalIds(actions, localIdToAction);
    }
}
