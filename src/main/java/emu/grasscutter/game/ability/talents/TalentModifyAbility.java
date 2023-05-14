package emu.grasscutter.game.ability.talents;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.binout.TalentData;
import emu.grasscutter.data.excels.ProudSkillData;
import emu.grasscutter.game.ability.Ability;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;

@TalentAction(TalentData.Type.ModifyAbility)
public class TalentModifyAbility extends TalentActionHandler {
    @Override
    public boolean execute(TalentData data, ProudSkillData skillData) {
        String abilityName = data.abilityName;

        String paramSpecial = data.paramSpecial;
        float ratio = data.paramRatio.get(skillData);
        float paramValue = (1f - ratio) * data.paramDelta.get(skillData);

        //Modify the result value
        if(!Ability.getAbilitySpecialsModified().containsKey(abilityName))
            Ability.getAbilitySpecialsModified().put(abilityName, new Object2FloatOpenHashMap<String>());

        if(Ability.getAbilitySpecialsModified().get(abilityName).containsKey(paramSpecial)) {
            paramValue += ratio * Ability.getAbilitySpecialsModified().get(abilityName).getFloat(paramSpecial);
        }
        Ability.getAbilitySpecialsModified().get(abilityName).put(paramSpecial, paramValue);
        //ability.getAbilitySpecials().put(paramSpecial, paramValue);

        return true;
    }
}
