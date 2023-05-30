package emu.grasscutter.game.ability.mixins;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import emu.grasscutter.data.binout.AbilityMixinData;

@Retention(RetentionPolicy.RUNTIME)
public @interface AbilityMixin {
    AbilityMixinData.Type value();
}
