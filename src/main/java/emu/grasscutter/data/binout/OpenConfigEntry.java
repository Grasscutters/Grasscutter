package emu.grasscutter.data.binout;

import emu.grasscutter.data.ResourceLoader.OpenConfigData;
import java.util.*;
import lombok.Getter;

@Getter
public class OpenConfigEntry {
    private final String name;
    private String[] addAbilities;
    private int extraTalentIndex;
    private SkillPointModifier[] skillPointModifiers;

    public OpenConfigEntry(String name, OpenConfigData[] data) {
        this.name = name;

        List<String> abilityList = new ArrayList<>();
        List<SkillPointModifier> modList = new ArrayList<>();

        for (OpenConfigData entry : data) {
            if (entry.$type.contains("AddAbility")) {
                abilityList.add(entry.abilityName);
            } else if (entry.talentIndex > 0) {
                this.extraTalentIndex = entry.talentIndex;
            } else if (entry.$type.contains("ModifySkillPoint")) {
                modList.add(new SkillPointModifier(entry.skillID, entry.pointDelta));
            }
        }

        if (!abilityList.isEmpty()) {
            this.addAbilities = abilityList.toArray(new String[0]);
        }

        if (!modList.isEmpty()) {
            this.skillPointModifiers = modList.toArray(new SkillPointModifier[0]);
        }
    }

    @Getter
    public static class SkillPointModifier {
        private final int skillId;
        private final int delta;

        public SkillPointModifier(int skillId, int delta) {
            this.skillId = skillId;
            this.delta = delta;
        }
    }
}
