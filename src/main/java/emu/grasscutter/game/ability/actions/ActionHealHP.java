package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.EntityClientGadget;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.props.FightProperty;

@AbilityAction(AbilityModifierAction.Type.HealHP)
public final class ActionHealHP extends AbilityActionHandler {
    @Override
    public boolean execute(
            Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        Grasscutter.getLogger().debug("Heal ability action executing 1");

        var owner = ability.getOwner();
        // handle client gadgets, that the effective caster is the current local avatar
        if (owner instanceof EntityClientGadget ownerGadget) {
            owner =
                    ownerGadget
                            .getScene()
                            .getEntityById(ownerGadget.getOwnerEntityId()); // Caster for EntityClientGadget
            Grasscutter.getLogger()
                    .debug(
                            "Owner {} has top owner {}: {}",
                            ability.getOwner(),
                            ownerGadget.getOwnerEntityId(),
                            owner);
        }

        if (owner == null) return false;

        ability
                .getAbilitySpecials()
                .forEach((k, v) -> Grasscutter.getLogger().trace(">>> {}: {}", k, v));

        var amountByCasterMaxHPRatio = action.amountByCasterMaxHPRatio.get(ability);
        var amountByCasterAttackRatio = action.amountByCasterAttackRatio.get(ability);
        var amountByCasterCurrentHPRatio = action.amountByCasterCurrentHPRatio.get(ability);
        var amountByTargetCurrentHPRatio = action.amountByTargetCurrentHPRatio.get(ability);
        var amountByTargetMaxHPRatio = action.amountByTargetMaxHPRatio.get(ability);

        Grasscutter.getLogger().trace("amountByCasterMaxHPRatio: " + amountByCasterMaxHPRatio);
        Grasscutter.getLogger().trace("amountByCasterAttackRatio: " + amountByCasterAttackRatio);
        Grasscutter.getLogger().trace("amountByCasterCurrentHPRatio: " + amountByCasterCurrentHPRatio);
        Grasscutter.getLogger().trace("amountByTargetCurrentHPRatio: " + amountByTargetCurrentHPRatio);
        Grasscutter.getLogger().trace("amountByTargetMaxHPRatio: " + amountByTargetMaxHPRatio);

        var amountToRegenerate = action.amount.get(ability);
        Grasscutter.getLogger().trace("Base amount: " + amountToRegenerate);

        amountToRegenerate +=
                amountByCasterMaxHPRatio * owner.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
        amountToRegenerate +=
                amountByCasterAttackRatio * owner.getFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK);
        amountToRegenerate +=
                amountByCasterCurrentHPRatio * owner.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);

        Grasscutter.getLogger().trace("amountToRegenerate: " + amountToRegenerate);

        var abilityRatio = 1.0f;
        Grasscutter.getLogger().trace("Base abilityRatio: " + abilityRatio);

        if (!action.ignoreAbilityProperty)
            abilityRatio +=
                    target.getFightProperty(FightProperty.FIGHT_PROP_HEAL_ADD)
                            + target.getFightProperty(FightProperty.FIGHT_PROP_HEALED_ADD);

        Grasscutter.getLogger().trace("abilityRatio: " + abilityRatio);

        Grasscutter.getLogger().trace("Sub-regenerate amount: " + amountToRegenerate);
        amountToRegenerate +=
                amountByTargetCurrentHPRatio * target.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
        amountToRegenerate +=
                amountByTargetMaxHPRatio * target.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);

        Grasscutter.getLogger().debug(">>> Healing {} without ratios", amountToRegenerate);
        target.heal(
                amountToRegenerate * abilityRatio * action.healRatio.get(ability, 1f),
                action.muteHealEffect);

        return true;
    }
}
