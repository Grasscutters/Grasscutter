package emu.grasscutter.data.def;

import java.util.List;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.PropGrowCurve;
import emu.grasscutter.data.custom.AbilityEmbryoEntry;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

@ResourceType(name = "AvatarExcelConfigData.json", loadPriority = LoadPriority.LOW)
public class AvatarData extends GameResource {
	
	private String name;
	private String IconName;
    private String BodyType;
    private String QualityType;
    private int ChargeEfficiency;
    private int InitialWeapon;
    private String WeaponType;
    private String ImageName;
    private int AvatarPromoteId;
    private String CutsceneShow;
    private int SkillDepotId;
    private int StaminaRecoverSpeed;
    private List<String> CandSkillDepotIds;
    private long DescTextMapHash;
    private String AvatarIdentityType;
    private List<Integer> AvatarPromoteRewardLevelList;
    private List<Integer> AvatarPromoteRewardIdList;
    private int FeatureTagGroupID;
    
    private long NameTextMapHash;
    private long GachaImageNameHashSuffix;
    private long InfoDescTextMapHash;
    
    private float HpBase;
    private float AttackBase;
    private float DefenseBase;
    private float Critical;
    private float CriticalHurt;

    private List<PropGrowCurve> PropGrowCurves;
    private int Id;
    
    private Int2ObjectMap<String> growthCurveMap;
    private float[] hpGrowthCurve;
    private float[] attackGrowthCurve;
    private float[] defenseGrowthCurve;
    private AvatarSkillDepotData skillDepot;
    private IntList abilities;

    private List<Integer> fetters;
    private int nameCardRewardId;
     private int nameCardId;
    
	@Override
	public int getId(){
        return this.Id;
    }
    
    public String getName() {
		return name;
	}
    
    public String getBodyType(){
        return this.BodyType;
    }
    
    public String getQualityType(){
        return this.QualityType;
    }
    
    public int getChargeEfficiency(){
        return this.ChargeEfficiency;
    }

    public int getInitialWeapon(){
        return this.InitialWeapon;
    }

    public String getWeaponType(){
        return this.WeaponType;
    }

    public String getImageName(){
        return this.ImageName;
    }

    public int getAvatarPromoteId(){
        return this.AvatarPromoteId;
    }

    public long getGachaImageNameHashSuffix(){
        return this.GachaImageNameHashSuffix;
    }

    public String getCutsceneShow(){
        return this.CutsceneShow;
    }

    public int getSkillDepotId(){
        return this.SkillDepotId;
    }

    public int getStaminaRecoverSpeed(){
        return this.StaminaRecoverSpeed;
    }

    public List<String> getCandSkillDepotIds(){
        return this.CandSkillDepotIds;
    }

    public long getDescTextMapHash(){
        return this.DescTextMapHash;
    }

    public String getAvatarIdentityType(){
        return this.AvatarIdentityType;
    }

    public List<Integer> getAvatarPromoteRewardLevelList(){
        return this.AvatarPromoteRewardLevelList;
    }

    public List<Integer> getAvatarPromoteRewardIdList(){
        return this.AvatarPromoteRewardIdList;
    }

    public int getFeatureTagGroupID(){
        return this.FeatureTagGroupID;
    }
    
    public long getInfoDescTextMapHash(){
        return this.InfoDescTextMapHash;
    }
    
    public float getBaseHp(int level){
    	try {
    		return this.HpBase * this.hpGrowthCurve[level - 1];
    	} catch (Exception e) {
    		return this.HpBase;
    	}
    }
    
    public float getBaseAttack(int level){
        try {
    		return this.AttackBase * this.attackGrowthCurve[level - 1];
    	} catch (Exception e) {
    		return this.AttackBase;
    	}
    }
    
    public float getBaseDefense(int level){
        try {
    		return this.DefenseBase * this.defenseGrowthCurve[level - 1];
    	} catch (Exception e) {
    		return this.DefenseBase;
    	}
    }
    
    public float getBaseCritical(){
        return this.Critical;
    }
    
    public float getBaseCriticalHurt(){
        return this.CriticalHurt;
    }
    
    public float getGrowthCurveById(int level, FightProperty prop) {
    	String growCurve = this.growthCurveMap.get(prop.getId());
    	if (growCurve == null) {
    		return 1f;
    	}
    	AvatarCurveData curveData = GameData.getAvatarCurveDataMap().get(level);
    	if (curveData == null) {
    		return 1f;
    	}
		return curveData.getCurveInfos().getOrDefault(growCurve, 1f);
	}

    public long getNameTextMapHash(){
        return this.NameTextMapHash;
    }
    
    public AvatarSkillDepotData getSkillDepot() {
		return skillDepot;
	}
    
	public IntList getAbilities() {
		return abilities;
	}

    public List<Integer> getFetters() {
        return fetters;
    }

    public int getNameCardRewardId() {
        return nameCardRewardId;
    }

    public int getNameCardId() {
        return nameCardId;
    }

	@Override
	public void onLoad() {
    	this.skillDepot = GameData.getAvatarSkillDepotDataMap().get(this.SkillDepotId);

        // Get fetters from GameData
        this.fetters = GameData.getFetterDataEntries().get(this.Id);
        
        if (GameData.getFetterCharacterCardDataMap().get(this.Id) != null) {
            this.nameCardRewardId = GameData.getFetterCharacterCardDataMap().get(this.Id).getRewardId();
        }

        if (GameData.getRewardDataMap().get(this.nameCardRewardId) != null) {
            this.nameCardId = GameData.getRewardDataMap().get(this.nameCardRewardId).getRewardItemList().get(0).getItemId();
        }
    	
    	int size = GameData.getAvatarCurveDataMap().size();
    	this.hpGrowthCurve = new float[size];
    	this.attackGrowthCurve = new float[size];
    	this.defenseGrowthCurve = new float[size];
    	for (AvatarCurveData curveData : GameData.getAvatarCurveDataMap().values()) {
    		int level = curveData.getLevel() - 1;
    		for (PropGrowCurve growCurve : this.PropGrowCurves) {
    			FightProperty prop = FightProperty.getPropByName(growCurve.getType());
    			switch (prop) {
    				case FIGHT_PROP_BASE_HP:
    					this.hpGrowthCurve[level] = curveData.getCurveInfos().get(growCurve.getGrowCurve());
    					break;
    				case FIGHT_PROP_BASE_ATTACK:
    					this.attackGrowthCurve[level] = curveData.getCurveInfos().get(growCurve.getGrowCurve());
    					break;
    				case FIGHT_PROP_BASE_DEFENSE:
    					this.defenseGrowthCurve[level] = curveData.getCurveInfos().get(growCurve.getGrowCurve());
    					break;
    				default:
    					break;
    			}
    		}
    	}
    	
    	/*
    	for (PropGrowCurve growCurve : this.PropGrowCurves) {
    		FightProperty prop = FightProperty.getPropByName(growCurve.getType());
    		this.growthCurveMap.put(prop.getId(), growCurve.getGrowCurve());
    	}
    	*/
    	
    	// Cache abilities
    	String[] split = this.IconName.split("_");
    	if (split.length > 0) {
    		this.name = split[split.length - 1];
    		
    		AbilityEmbryoEntry info = GameData.getAbilityEmbryoInfo().get(this.name);
    		if (info != null) {
    			this.abilities = new IntArrayList(info.getAbilities().length);
    			for (String ability : info.getAbilities()) {
    				this.abilities.add(Utils.abilityHash(ability));
    			}
    		}
    	}
    }
}

