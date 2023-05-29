package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.EntityClientGadget;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.props.FightProperty;

@AbilityAction(AbilityModifierAction.Type.HealHP)
public class ActionHealHP extends AbilityActionHandler {
    @Override
    public boolean execute(Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        Grasscutter.getLogger().info("Heal ability action executing 1\n");

        GameEntity owner = ability.getOwner();
        //handle client gadgets, that the effective caster is the current local avatar
        if(owner instanceof EntityClientGadget ownerGadget) {
            owner = ownerGadget.getScene().getEntityById(ownerGadget.getOwnerEntityId()); //Caster for EntityClientGadget
            Grasscutter.getLogger().info("Owner {} has top owner {}: {}", ability.getOwner(), ownerGadget.getOwnerEntityId(), owner);
        }
        if(owner == null) return false;

        float amountByCasterMaxHPRatio = action.amountByCasterMaxHPRatio.get(ability);
        float amountByCasterAttackRatio = action.amountByCasterAttackRatio.get(ability);
        float amountByCasterCurrentHPRatio = action.amountByCasterCurrentHPRatio.get(ability);
        float amountByTargetCurrentHPRatio = action.amountByTargetCurrentHPRatio.get(ability);
        float amountByTargetMaxHPRatio = action.amountByTargetMaxHPRatio.get(ability);
        float amount = action.amount.get(ability);

        float amountToRegenerate = amount;
        amountToRegenerate += amountByCasterMaxHPRatio * owner.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
        amountToRegenerate += amountByCasterAttackRatio * owner.getFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK);
        amountToRegenerate += amountByCasterCurrentHPRatio * owner.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);

        float abilityRatio = 1.0f;
        if(!action.ignoreAbilityProperty) abilityRatio += target.getFightProperty(FightProperty.FIGHT_PROP_HEAL_ADD) + target.getFightProperty(FightProperty.FIGHT_PROP_HEALED_ADD);

        amountToRegenerate += amountByTargetCurrentHPRatio * target.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
        amountToRegenerate += amountByTargetMaxHPRatio * target.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);

        Grasscutter.getLogger().info("Healing {} without ratios\n", amountToRegenerate);
        target.heal(amountToRegenerate * abilityRatio * action.healRatio.get(ability, 1f), action.muteHealEffect);

        return true;
    }
}
