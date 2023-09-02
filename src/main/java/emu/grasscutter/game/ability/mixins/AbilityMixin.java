package emu.grasscutter.game.ability.mixins;

import emu.grasscutter.data.binout.AbilityMixinData;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface AbilityMixin {
    AbilityMixinData.Type value();
}
