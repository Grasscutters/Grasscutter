package emu.grasscutter.data.binout;

import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.AbilityLocalIdGenerator;
import emu.grasscutter.game.ability.AbilityLocalIdGenerator.ConfigAbilitySubContainerType;
import java.util.*;

public class AbilityData {
    public String abilityName;
    public Map<String, AbilityModifier> modifiers;
    public boolean isDynamicAbility;
    public Map<String, Float> abilitySpecials;

    public AbilityModifierAction[] onAdded;
    public AbilityModifierAction[] onRemoved;
    public AbilityModifierAction[] onAbilityStart;
    public AbilityModifierAction[] onKill;
    public AbilityModifierAction[] onFieldEnter;
    public AbilityModifierAction[] onExit;
    public AbilityModifierAction[] onAttach;
    public AbilityModifierAction[] onDetach;
    public AbilityModifierAction[] onAvatarIn;
    public AbilityModifierAction[] onAvatarOut;
    public AbilityModifierAction[] onTriggerAvatarRay;
    public AbilityModifierAction[] onVehicleIn;
    public AbilityModifierAction[] onVehicleOut;

    // abilityMixins
    public AbilityMixinData[] abilityMixins;

    public final Map<Integer, AbilityModifierAction> localIdToAction = new HashMap<>();
    public final Map<Integer, AbilityMixinData> localIdToMixin = new HashMap<>();

    private boolean _initialized = false;

    public void initialize() {
        if (_initialized) return;
        _initialized = true;

        initializeMixins();
        initializeModifiers();
        initializeActions();
    }

    private void initializeActions() {
        AbilityLocalIdGenerator generator =
                new AbilityLocalIdGenerator(ConfigAbilitySubContainerType.ACTION);
        generator.configIndex = 0;

        generator.initializeActionLocalIds(onAdded, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onRemoved, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onAbilityStart, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onKill, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onFieldEnter, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onExit, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onAttach, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onDetach, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onAvatarIn, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onAvatarOut, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onTriggerAvatarRay, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onVehicleIn, localIdToAction);
        generator.configIndex++;
        generator.initializeActionLocalIds(onVehicleOut, localIdToAction);
    }

    private void initializeMixins() {
        if (abilityMixins != null) {
            AbilityLocalIdGenerator generator =
                    new AbilityLocalIdGenerator(ConfigAbilitySubContainerType.MIXIN);
            generator.modifierIndex = 0;
            generator.configIndex = 0;

            generator.initializeMixinsLocalIds(abilityMixins, localIdToMixin);
        }
    }

    private void initializeModifiers() {
        if (modifiers == null) {
            this.modifiers = new HashMap<>();
            return;
        }

        var _modifiers =
                modifiers.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .map(Map.Entry::getValue)
                        .toList();

        var modifierIndex = 0;
        for (AbilityModifier abilityModifier : _modifiers) {
            long configIndex = 0L;
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onAdded, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onRemoved, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onBeingHit, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onAttackLanded, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onHittingOther, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onThinkInterval, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onKill, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onCrash, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onAvatarIn, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onAvatarOut, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onReconnect, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onChangeAuthority, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onVehicleIn, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onVehicleOut, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onZoneEnter, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onZoneExit, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onHeal, localIdToAction);
            this.initializeActionSubCategory(
                    modifierIndex, configIndex++, abilityModifier.onBeingHealed, localIdToAction);

            if (abilityModifier.modifierMixins != null) {
                var generator = new AbilityLocalIdGenerator(ConfigAbilitySubContainerType.MODIFIER_MIXIN);
                generator.modifierIndex = modifierIndex;
                generator.configIndex = 0;

                generator.initializeMixinsLocalIds(abilityModifier.modifierMixins, localIdToMixin);
            }

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
