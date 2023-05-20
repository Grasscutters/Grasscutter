package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;

import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.GameEntity;

@AbilityAction(AbilityModifierAction.Type.SetGlobalValueToOverrideMap)
public class ActionSetGlobalValueToOverrideMap extends AbilityActionHandler {
    @Override
    public boolean execute(Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        //TODO:
        var entity = target;
        if(action.isFromOwner) {
            if(target instanceof EntityGadget gadget)
                entity = gadget.getOwner();
        }

        var globalValueKey = action.globalValueKey;
        var abilityFormula = action.abilityFormula;

        if(!ability.getAbilitySpecials().containsKey(globalValueKey))
            return false;

        var globalValue = ability.getAbilitySpecials().getOrDefault(globalValueKey, 0.0f);
        if(abilityFormula.compareTo("DummyThrowSpeed") == 0) {
            globalValue = ((globalValue * 30.0f) / ((float)Math.sin(0.9424778) * 100.0f)) - 1.0f;
        }

        ability.getAbilitySpecials().put(globalValueKey, globalValue);

        return true;
    }
}
