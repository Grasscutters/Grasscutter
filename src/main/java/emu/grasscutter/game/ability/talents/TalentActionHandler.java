package emu.grasscutter.game.ability.talents;

import emu.grasscutter.data.binout.TalentData;
import emu.grasscutter.data.excels.ProudSkillData;
import emu.grasscutter.game.ability.Ability;

public abstract class TalentActionHandler {

    public abstract boolean execute(TalentData data, ProudSkillData skillData);

}
