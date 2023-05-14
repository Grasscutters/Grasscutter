package emu.grasscutter.game.ability.talents;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import emu.grasscutter.data.binout.TalentData;

@Retention(RetentionPolicy.RUNTIME)
public @interface TalentAction {
    TalentData.Type value();
}
