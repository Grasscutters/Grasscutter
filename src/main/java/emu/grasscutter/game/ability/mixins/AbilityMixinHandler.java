package emu.grasscutter.game.ability.mixins;

import com.google.protobuf.ByteString;
import emu.grasscutter.data.binout.AbilityMixinData;
import emu.grasscutter.game.ability.Ability;

public abstract class AbilityMixinHandler {

    public abstract boolean execute(
            Ability ability, AbilityMixinData mixinData, ByteString abilityData);
}
