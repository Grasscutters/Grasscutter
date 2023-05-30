package emu.grasscutter.game.ability.actions;

import com.google.protobuf.ByteString;

import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import emu.grasscutter.game.ability.Ability;
import emu.grasscutter.game.entity.GameEntity;

public abstract class AbilityActionHandler {

    public abstract boolean execute(Ability ability, AbilityModifierAction action, ByteString abilityData, GameEntity target);

}
