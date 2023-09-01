package emu.grasscutter.game.ability.actions;

import com.google.protobuf.*;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.GameEntity;
import emu.grasscutter.net.proto.AbilityActionSetRandomOverrideMapValueOuterClass.AbilityActionSetRandomOverrideMapValue;

@AbilityAction(AbilityModifierAction.Type.SetRandomOverrideMapValue)
public class ActionSetRandomOverrideMapValue extends AbilityActionHandler {
    @Override
    public boolean execute(
            Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target) {
        AbilityActionSetRandomOverrideMapValue valueProto;
        try {
            valueProto = AbilityActionSetRandomOverrideMapValue.parseFrom(abilityData);
        } catch (InvalidProtocolBufferException e) {
            return false;
        }

        float value = valueProto.getRandomValue();
        float valueRangeMin = action.valueRangeMin.get(ability);
        float valueRangeMax = action.valueRangeMax.get(ability);

        if (value < valueRangeMin || value > valueRangeMax) {
            Grasscutter.getLogger()
                    .warn(
                            "Tried setting value out of range: {} inside [{}, {}]",
                            value,
                            valueRangeMin,
                            valueRangeMax);
            return true;
        }

        ability.getAbilitySpecials().put(action.overrideMapKey, value);

        return true;
    }
}
