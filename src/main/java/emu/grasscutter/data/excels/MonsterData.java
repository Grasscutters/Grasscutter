package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.PropGrowCurve;

@ResourceType(name = "MonsterExcelConfigData.json", loadPriority = LoadPriority.LOW)
public class MonsterData extends GameResource {
	private int id;
	
	private String monsterName;
    private String type;
    private String serverScript;
    private List<Integer> affix;
    private String ai;
    private int[] equips;
    private List<HpDrops> hpDrops;
    private int killDropId;
    private String excludeWeathers;
    private int featureTagGroupID;
    private int mpPropID;
    private String skin;
    private int describeId;
    private int combatBGMLevel;
    private int entityBudgetLevel;
    private float hpBase;
    private float attackBase;
    private float defenseBase;
    private float fireSubHurt;
    private float elecSubHurt;
    private float grassSubHurt;
    private float waterSubHurt;
    private float windSubHurt;
    private float rockSubHurt;
    private float iceSubHurt;
    private float physicalSubHurt;
    private List<PropGrowCurve> propGrowCurves;
    private long nameTextMapHash;
    private int campID;
    
    // Transient
    private int weaponId;
    private MonsterDescribeData describeData;
    
	@Override
	public int getId() {
		return this.id;
	}
	
	public String getMonsterName() {
		return monsterName;
	}

	public String getType() {
		return type;
	}

	public String getServerScript() {
		return serverScript;
	}

	public List<Integer> getAffix() {
		return affix;
	}

	public String getAi() {
		return ai;
	}

	public int[] getEquips() {
		return equips;
	}

	public List<HpDrops> getHpDrops() {
		return hpDrops;
	}

	public int getKillDropId() {
		return killDropId;
	}

	public String getExcludeWeathers() {
		return excludeWeathers;
	}

	public int getFeatureTagGroupID() {
		return featureTagGroupID;
	}

	public int getMpPropID() {
		return mpPropID;
	}

	public String getSkin() {
		return skin;
	}

	public int getDescribeId() {
		return describeId;
	}

	public int getCombatBGMLevel() {
		return combatBGMLevel;
	}

	public int getEntityBudgetLevel() {
		return entityBudgetLevel;
	}

	public float getBaseHp() {
		return hpBase;
	}

	public float getBaseAttack() {
		return attackBase;
	}

	public float getBaseDefense() {
		return defenseBase;
	}

	public float getElecSubHurt() {
		return elecSubHurt;
	}

	public float getGrassSubHurt() {
		return grassSubHurt;
	}

	public float getWaterSubHurt() {
		return waterSubHurt;
	}

	public float getWindSubHurt() {
		return windSubHurt;
	}

	public float getIceSubHurt() {
		return iceSubHurt;
	}

	public float getPhysicalSubHurt() {
		return physicalSubHurt;
	}

	public List<PropGrowCurve> getPropGrowCurves() {
		return propGrowCurves;
	}

	public long getNameTextMapHash() {
		return nameTextMapHash;
	}

	public int getCampID() {
		return campID;
	}

	public MonsterDescribeData getDescribeData() {
		return describeData;
	}

	public int getWeaponId() {
		return weaponId;
	}

	@Override
	public void onLoad() {
		this.describeData = GameData.getMonsterDescribeDataMap().get(this.getDescribeId());
		
		for (int id : this.equips) {
			if (id == 0) {
				continue;
			}
			GadgetData gadget = GameData.getGadgetDataMap().get(id);
			if (gadget == null) {
				continue;
			}
			if (gadget.getItemJsonName().equals("Default_MonsterWeapon")) {
				this.weaponId = id;
			}
		}
	}
	
	public class HpDrops {
	    private int DropId;
	    private int HpPercent;

	    public int getDropId(){
	        return this.DropId;
	    }
	    public int getHpPercent(){
	        return this.HpPercent;
	    }
	}
}
