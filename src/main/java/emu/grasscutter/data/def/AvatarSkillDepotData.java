package emu.grasscutter.data.def;

import java.util.List;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.custom.AbilityEmbryoEntry;
import emu.grasscutter.game.props.ElementType;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

@ResourceType(name = "AvatarSkillDepotExcelConfigData.json", loadPriority = LoadPriority.HIGH)
public class AvatarSkillDepotData extends GameResource {
	
    private int Id;
    private int EnergySkill;
    private int AttackModeSkill;

    private List<Integer> Skills;
    private List<Integer> SubSkills;
    private List<String> ExtraAbilities;
    private List<Integer> Talents;
    private List<InherentProudSkillOpens> InherentProudSkillOpens;

    private String TalentStarName;
    private String SkillDepotAbilityGroup;
    
    private AvatarSkillData energySkillData;
    private ElementType elementType;
    private IntList abilities;

    @Override
	public int getId(){
        return this.Id;
    }
    
    public int getEnergySkill(){
        return this.EnergySkill;
    }
    
    public List<Integer> getSkills(){
        return this.Skills;
    }
    
    public List<Integer> getSubSkills(){
        return this.SubSkills;
    }
    
    public int getAttackModeSkill(){
        return this.AttackModeSkill;
    }
    
    public List<String> getExtraAbilities(){
        return this.ExtraAbilities;
    }
    
    public List<Integer> getTalents(){
        return this.Talents;
    }
    
    public String getTalentStarName(){
        return this.TalentStarName;
    }
    
    public List<InherentProudSkillOpens> getInherentProudSkillOpens(){
        return this.InherentProudSkillOpens;
    }
    
    public String getSkillDepotAbilityGroup(){
        return this.SkillDepotAbilityGroup;
    }
    
	public AvatarSkillData getEnergySkillData() {
		return this.energySkillData;
	}
	
	public ElementType getElementType() {
		return elementType;
	}
    
    public IntList getAbilities() {
		return abilities;
	}
    
	public void setAbilities(AbilityEmbryoEntry info) {
		this.abilities = new IntArrayList(info.getAbilities().length);
		for (String ability : info.getAbilities()) {
			this.abilities.add(Utils.abilityHash(ability));
		}
	}
	
	@Override
	public void onLoad() {
    	this.energySkillData = GameData.getAvatarSkillDataMap().get(this.EnergySkill);
    	if (getEnergySkillData() != null) {
    		this.elementType = ElementType.getTypeByName(getEnergySkillData().getCostElemType());
    	} else {
    		this.elementType = ElementType.None;
    	}
    }
    
    public static class InherentProudSkillOpens {
        private int ProudSkillGroupId;

        private int NeedAvatarPromoteLevel;

        public void setProudSkillGroupId(int ProudSkillGroupId){
            this.ProudSkillGroupId = ProudSkillGroupId;
        }
        public int getProudSkillGroupId(){
            return this.ProudSkillGroupId;
        }
        public void setNeedAvatarPromoteLevel(int NeedAvatarPromoteLevel){
            this.NeedAvatarPromoteLevel = NeedAvatarPromoteLevel;
        }
        public int getNeedAvatarPromoteLevel(){
            return this.NeedAvatarPromoteLevel;
        }
    }
}
