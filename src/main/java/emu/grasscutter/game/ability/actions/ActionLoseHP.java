package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.EntityClientGadget;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.game.props.FightProperty;

@AbilityAction(AbilityModifierAction.Type.LoseHP)
public class ActionLoseHP extends AbilityActionHandler {
    @Override
    public boolean execute(Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        GameEntity owner = ability.getOwner();
        //handle client gadgets, that the effective caster is the current local avatar
        if(owner instanceof EntityClientGadget ownerGadget) {
            owner = ownerGadget.getScene().getEntityById(ownerGadget.getOwnerEntityId()); //Caster for EntityClientGadget

            //TODO: Do this per entity, not just the player
            if(ownerGadget.getOwner().getAbilityManager().isAbilityInvulnerable()) return true;
        }
        if(owner == null || target == null) return false;

        if(action.enableLockHP && target.isLockHP()) {
            return true;
        }

        if(action.disableWhenLoading && target.getScene().getWorld().getHost().getSceneLoadState().getValue() < 2) {
            return true;
        }

        float amountByCasterMaxHPRatio = action.amountByCasterMaxHPRatio.get(ability);
        float amountByCasterAttackRatio = action.amountByCasterAttackRatio.get(ability);
        float amountByCasterCurrentHPRatio = action.amountByCasterCurrentHPRatio.get(ability); //Seems unused on server
        float amountByTargetCurrentHPRatio = action.amountByTargetCurrentHPRatio.get(ability);
        float amountByTargetMaxHPRatio = action.amountByTargetMaxHPRatio.get(ability);
        float limboByTargetMaxHPRatio = action.limboByTargetMaxHPRatio.get(ability);
        float amount = action.amount.get(ability);

        float amountToLose = amount;
        amountToLose += amountByCasterMaxHPRatio * owner.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
        amountToLose += amountByCasterAttackRatio * owner.getFightProperty(FightProperty.FIGHT_PROP_CUR_ATTACK);
        amountToLose += amountByCasterCurrentHPRatio * owner.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);

        float currentHp = target.getFightProperty(FightProperty.FIGHT_PROP_CUR_HP);
        float maxHp = target.getFightProperty(FightProperty.FIGHT_PROP_MAX_HP);
        amountToLose += amountByTargetCurrentHPRatio * currentHp;
        amountToLose += amountByTargetMaxHPRatio * maxHp;

        if(limboByTargetMaxHPRatio > 1.192093e-07)
            amountToLose = (float)Math.min(Math.max(currentHp -  Math.max(limboByTargetMaxHPRatio * maxHp, 1.0), 0.0), amountToLose);

        if(currentHp < (amountToLose + 0.01) && !action.lethal)
            amountToLose = 0;

        target.damage(amountToLose);

        return true;
    }
}
