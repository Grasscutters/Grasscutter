package emu.grasscutter.game.ability;

import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;

public abstract class AbilityActionHandler {
    public abstract boolean execute(Ability ability, AbilityModifierAction action);
}
