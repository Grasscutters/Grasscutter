package emu.grasscutter.data.excels;

import java.util.List;
import java.util.stream.IntStream;

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
import lombok.Getter;

@ResourceType(name = "AvatarSkillDepotExcelConfigData.json", loadPriority = LoadPriority.HIGH)
public class AvatarSkillDepotData extends GameResource {

    private int id;
    @Getter private int energySkill;
    @Getter private int attackModeSkill;

    @Getter private List<Integer> skills;
    @Getter private List<Integer> subSkills;
    @Getter private List<String> extraAbilities;
    @Getter private List<Integer> talents;
    @Getter private List<InherentProudSkillOpens> inherentProudSkillOpens;

    @Getter private String talentStarName;
    @Getter private String skillDepotAbilityGroup;

    // Transient
    @Getter private AvatarSkillData energySkillData;
    @Getter private ElementType elementType;
    @Getter private IntList abilities;

    @Override
    public int getId() {
        return this.id;
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
        if (this.energySkillData != null) {
            this.elementType = this.energySkillData.getCostElemType();
        } else {
            this.elementType = ElementType.None;
        }
        // Set embryo abilities (if player skill depot)
        if (getSkillDepotAbilityGroup() != null && getSkillDepotAbilityGroup().length() > 0) {
            AvatarConfig config = GameDepot.getPlayerAbilities().get(getSkillDepotAbilityGroup());

            if (config != null) {
                this.setAbilities(new AbilityEmbryoEntry(getSkillDepotAbilityGroup(), config.abilities.stream().map(Object::toString).toArray(String[]::new)));
            }
        }
    }

    public static class InherentProudSkillOpens {
        @Getter private int proudSkillGroupId;
        @Getter private int needAvatarPromoteLevel;
    }

    public IntStream getSkillsAndEnergySkill() {
        return IntStream.concat(this.skills.stream().mapToInt(i -> i), IntStream.of(this.energySkill))
                        .filter(skillId -> skillId > 0);
    }
}
