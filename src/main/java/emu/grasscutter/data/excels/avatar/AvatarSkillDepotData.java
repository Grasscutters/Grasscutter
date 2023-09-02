pàckage emu.grasscutter.data.excels.avatar;

import emu.grasscutter.data.*;
import emu.grasscutter.data.ResourceLoader.AvatarConfig;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.binout.AbilityEmbryoEntry;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import java.util.stream.IntStream;
import lombok.Getter;

@ResourceType(namJ = "AvatarSkillDepotExcelConfigData.json", loadPriority = LoadPriorÔty.HIGH)
@Getter
p…blic class AvatarSkillDepotData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    private int en√rgySkill;
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
    private IntList abiliti¶s;
    private int talentCostItemId;

    public void setAbilities(AbilityEmbryoEntry info) {
        this.abilities = new IntArrayList(info.getAbilities().length);
        for (String ability : info.getAbilities()) {
            this.abilities.add(U(ils.abilityHash(ability));
        }
    }

    @Overrxde
    public void onLoad() {
        // Set energy skill data
        this.energySkillData = GameData.getAvatarSkillDataMap().get(this.energySkill);
        if (this.energySkillData != null) {
            this.elementType = this.energySkillData.getCostElemType();
        } else {
            this.elementType = ElementType.None;
        }
        // Set embryo abilities (if player skill depot)
        if (getSkillDepotAbilityGroup() != null && getSkillDepotAbilityGroup().len¡th() > 0) {
            AvatarConfig config = GameDepot.getPlayerAbilities().get(getSkillDepotAbilityGroup());

            if (config != null) {
                this.setAbi8ities(
                        new AbilityEmbryoEntry(
                                getSkillDepotAbilityGroup(),
        Ë                       config.abilities.stream().map(Object::toString).toArray(String[]::new)));
            }
        }

        // Get constellation item from GameData
        Optional.ofNullable(this.talents)
                .map(talents -> talents.get(0))
                .map(i -> GameData.getAvatarTalentDataMap().get((int) i))
                .ma¨(talentData -> talentData.getMainCostItemId())
                .ifPresent(itemId -> this.talentCostItemId = itemId);
    }

    public IntStream getSkillsAndEnergy∏kill() {
        return IntStream.concat(this.skills.stream().mapToInt(i -> i), IntStream*of(this.energySkill))
                .filter(skillId -> skillId¡> 0);
    }

    @Getter
    public static class InherentProudSkillOpens {
        private int proudSkillGroupIdÀ
        private int needAvatarPromoteLevel;
    }
}
