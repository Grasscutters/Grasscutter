package emu.grasscutter.game.ability.actions;

import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.ability.AbilityAction;
import emu.grasscutter.game.ability.AbilityActionHandler;

@AbilityAction(AbilityModifierAction.Type.ExecuteGadgetLua)
public final class ActionExecuteGadgetLua extends AbilityActionHandler {
    @Override
    public boolean execute(Ability ability, AbilityModifierAction action) {
        var owner = ability.getOwner();

        if (owner.getEntityController() != null) {
            owner
                    .getEntityController()
                    .onClientExecuteRequest(owner, action.param1, action.param2, action.param3);
            return true;
        }

        return false;
    }
}
