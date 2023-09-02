package emu.grasscutter.game.ability.actions;

import emu.grasscutter.data.binout.AbilityModifier.AbilityModifierAction;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface AbilityAction {
    AbilityModifierAction.Type value();
}
