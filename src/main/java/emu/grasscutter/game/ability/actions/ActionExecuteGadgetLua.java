package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.GameEntity;

@AbilityAction(AbilityModifierAction.Type.ExecuteGadgetLua)
public class ActionExecuteGadgetLua extends AbilityActionHandler {
    @Override
    public boolean execute(
            Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        GameEntity owner = ability.getOwner();

        // Investigate if we need to use target

        if (owner.getEntityController() != null) {
            owner
                    .getEntityController()
                    .onClientExecuteRequest(owner, action.param1, action.param2, action.param3);
            return true;
        }

        return false;
    }
}
