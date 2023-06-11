package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.GameEntity;

@AbilityAction(AbilityModifierAction.Type.KillSelf)
public final class ActionKillSelf extends AbilityActionHandler {
    @Override
    public boolean execute(
            Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        // KillSelf should not have a target field, so target it's the actual entity to be applied.
        // TODO: Check if this is always true.
        if (target == null) {
            Grasscutter.getLogger().warn("Tried killing null target");
            return false;
        }

        target.getScene().killEntity(target);
        return true;
    }
}
