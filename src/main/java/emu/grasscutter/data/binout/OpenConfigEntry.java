package emu.grasscutter.data.binout;

import emu.grasscutter.data.ResourceLoader.OpenConfigData;

import java.util.ArrayList;
import java.util.List;

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

        if (abilityList.size() > 0) {
            this.addAbilities = abilityList.toArray(new String[0]);
        }

        if (modList.size() > 0) {
            this.skillPointModifiers = modList.toArray(new SkillPointModifier[0]);
        }
    }

    public String getName() {
        return this.name;
    }

    public String[] getAddAbilities() {
        return this.addAbilities;
    }

    public int getExtraTalentIndex() {
        return this.extraTalentIndex;
    }

    public SkillPointModifier[] getSkillPointModifiers() {
        return this.skillPointModifiers;
    }

    public static class SkillPointModifier {
        private final int skillId;
        private final int delta;

        public SkillPointModifier(int skillId, int delta) {
            this.skillId = skillId;
            this.delta = delta;
        }

        public int getSkillId() {
            return this.skillId;
        }

        public int getDelta() {
            return this.delta;
        }
    }
}
