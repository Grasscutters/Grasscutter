package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameDepot;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceLoader.AvatarConfig;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.binout.AbilityEmbryoEntry;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.util.List;

@ResourceType(name = "AvatarSkillDepotExcelConfigData.json", loadPriority = LoadPriority.HIGH)
public class AvatarSkillDepotData extends GameResource {

    private int id;
    private int energySkill;
    private int attackModeSkill;

    private List<Integer> skills;
    private List<Integer> subSkills;
    private List<String> extraAbilities;
    private List<Integer> talents;
    private List<InherentProudSkillOpens> inherentProudSkillOpens;

    private String talentStarName;
    private String skillDepotAbilityGroup;

    // Transient
    private AvatarSkillData energySkillData;
    private ElementType elementType;
    private IntList abilities;

    @Override
    public int getId() {
        return this.id;
    }

    public int getEnergySkill() {
        return this.energySkill;
    }

    public List<Integer> getSkills() {
        return this.skills;
    }

    public List<Integer> getSubSkills() {
        return this.subSkills;
    }

    public int getAttackModeSkill() {
        return this.attackModeSkill;
    }

    public List<String> getExtraAbilities() {
        return this.extraAbilities;
    }

    public List<Integer> getTalents() {
        return this.talents;
    }

    public String getTalentStarName() {
        return this.talentStarName;
    }

    public List<InherentProudSkillOpens> getInherentProudSkillOpens() {
        return this.inherentProudSkillOpens;
    }

    public String getSkillDepotAbilityGroup() {
        return this.skillDepotAbilityGroup;
    }

    public AvatarSkillData getEnergySkillData() {
        return this.energySkillData;
    }

    public ElementType getElementType() {
        return this.elementType;
    }

    public IntList getAbilities() {
        return this.abilities;
    }

    public void setAbilities(AbilityEmbryoEntry info) {
        this.abilities = new IntArrayList(info.getAbilities().length);
        for (String ability : info.getAbilities()) {
            this.abilities.add(Utils.abilityHash(ability));
        }
    }

    @Override
    public void onLoad() {
        // Set energy skill data
        this.energySkillData = GameData.getAvatarSkillDataMap().get(this.energySkill);
        if (this.getEnergySkillData() != null) {
            this.elementType = this.getEnergySkillData().getCostElemType();
        } else {
            this.elementType = ElementType.None;
        }
        // Set embryo abilities (if player skill depot)
        if (this.getSkillDepotAbilityGroup() != null && this.getSkillDepotAbilityGroup().length() > 0) {
            AvatarConfig config = GameDepot.getPlayerAbilities().get(this.getSkillDepotAbilityGroup());

            if (config != null) {
                this.setAbilities(new AbilityEmbryoEntry(this.getSkillDepotAbilityGroup(), config.abilities.stream().map(Object::toString).toArray(String[]::new)));
            }
        }
    }

    public static class InherentProudSkillOpens {
        private int proudSkillGroupId;
        private int needAvatarPromoteLevel;

        public int getProudSkillGroupId() {
            return this.proudSkillGroupId;
        }

        public int getNeedAvatarPromoteLevel() {
            return this.needAvatarPromoteLevel;
        }
    }
}
