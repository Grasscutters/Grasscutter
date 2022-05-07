package emu.grasscutter.data.def;

import java.util.List;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.PropGrowCurve;

@ResourceType(name = "MonsterExcelConfigData.json", loadPriority = LoadPriority.LOW)
public class MonsterData extends GameResource {
	private int Id;
	
	private String MonsterName;
    private String Type;
    private String ServerScript;
    private List<Integer> Affix;
    private String Ai;
    private int[] Equips;
    private List<HpDrops> HpDrops;
    private int KillDropId;
    private String ExcludeWeathers;
    private int FeatureTagGroupID;
    private int MpPropID;
    private String Skin;
    private int DescribeId;
    private int CombatBGMLevel;
    private int EntityBudgetLevel;
    private float HpBase;
    private float AttackBase;
    private float DefenseBase;
    private float FireSubHurt;
    private float ElecSubHurt;
    private float GrassSubHurt;
    private float WaterSubHurt;
    private float WindSubHurt;
    private float RockSubHurt;
    private float IceSubHurt;
    private float PhysicalSubHurt;
    private List<PropGrowCurve> PropGrowCurves;
    private long NameTextMapHash;
    private int CampID;
    
    private int weaponId;
    private MonsterDescribeData describeData;
    
	@Override
	public int getId() {
		return this.Id;
	}
	
	public String getMonsterName() {
		return MonsterName;
	}

	public String getType() {
		return Type;
	}

	public String getServerScript() {
		return ServerScript;
	}

	public List<Integer> getAffix() {
		return Affix;
	}

	public String getAi() {
		return Ai;
	}

	public int[] getEquips() {
		return Equips;
	}

	public List<HpDrops> getHpDrops() {
		return HpDrops;
	}

	public int getKillDropId() {
		return KillDropId;
	}

	public String getExcludeWeathers() {
		return ExcludeWeathers;
	}

	public int getFeatureTagGroupID() {
		return FeatureTagGroupID;
	}

	public int getMpPropID() {
		return MpPropID;
	}

	public String getSkin() {
		return Skin;
	}

	public int getDescribeId() {
		return DescribeId;
	}

	public int getCombatBGMLevel() {
		return CombatBGMLevel;
	}

	public int getEntityBudgetLevel() {
		return EntityBudgetLevel;
	}

	public float getBaseHp() {
		return HpBase;
	}

	public float getBaseAttack() {
		return AttackBase;
	}

	public float getBaseDefense() {
		return DefenseBase;
	}

	public float getElecSubHurt() {
		return ElecSubHurt;
	}

	public float getGrassSubHurt() {
		return GrassSubHurt;
	}

	public float getWaterSubHurt() {
		return WaterSubHurt;
	}

	public float getWindSubHurt() {
		return WindSubHurt;
	}

	public float getIceSubHurt() {
		return IceSubHurt;
	}

	public float getPhysicalSubHurt() {
		return PhysicalSubHurt;
	}

	public List<PropGrowCurve> getPropGrowCurves() {
		return PropGrowCurves;
	}

	public long getNameTextMapHash() {
		return NameTextMapHash;
	}

	public int getCampID() {
		return CampID;
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
		
		for (int id : this.Equips) {
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
