package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.props.FightProperty;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

@ResourceType(name = {"MaterialExcelConfigData.json", "WeaponExcelConfigData.json", "ReliquaryExcelConfigData.json"})
public class ItemData extends GameResource {
	
	private int Id;
    private int StackLimit = 1;
    private int MaxUseCount;
    private int RankLevel;
    private String EffectName;
    private int[] SatiationParams;
    private int Rank;
    private int Weight;
    private int GadgetId;
    
    private int[] DestroyReturnMaterial;
    private int[] DestroyReturnMaterialCount;
    
    // Food
    private String FoodQuality;
    private String UseTarget;
    private String[] UseParam;
    
    // String enums
    private String ItemType;
    private String MaterialType;
    private String EquipType;
    private String EffectType;
    private String DestroyRule;
    
    // Relic
    private int MainPropDepotId;
    private int AppendPropDepotId;
    private int AppendPropNum;
    private int SetId;
    private int[] AddPropLevels;
    private int BaseConvExp;
    private int MaxLevel;
    
    // Weapon
    private int WeaponPromoteId;
    private int WeaponBaseExp;
    private int StoryId;
    private int AvatarPromoteId;
    private int AwakenMaterial;
    private int[] AwakenCosts;
    private int[] SkillAffix;
    private WeaponProperty[] WeaponProp;
    
    // Hash
    private String Icon;
    private long NameTextMapHash;
    
    // Post load
    private transient emu.grasscutter.game.inventory.MaterialType materialType;
    private transient emu.grasscutter.game.inventory.ItemType itemType;
    private transient emu.grasscutter.game.inventory.EquipType equipType;
    
    private IntSet addPropLevelSet;
    
    @Override
	public int getId(){
        return this.Id;
    }
    
    public String getMaterialTypeString(){
        return this.MaterialType;
    }
    
    public int getStackLimit(){
        return this.StackLimit;
    }
    
    public int getMaxUseCount(){
        return this.MaxUseCount;
    }
    
    public String getUseTarget(){
        return this.UseTarget;
    }
    
    public String[] getUseParam(){
        return this.UseParam;
    }
    
    public int getRankLevel(){
        return this.RankLevel;
    }
    
    public String getFoodQuality(){
        return this.FoodQuality;
    }
    
    public String getEffectName(){
        return this.EffectName;
    }
    
    public int[] getSatiationParams(){
        return this.SatiationParams;
    }
    
    public int[] getDestroyReturnMaterial(){
        return this.DestroyReturnMaterial;
    }
    
    public int[] getDestroyReturnMaterialCount(){
        return this.DestroyReturnMaterialCount;
    }
    
    public long getNameTextMapHash(){
        return this.NameTextMapHash;
    }
    
    public String getIcon(){
        return this.Icon;
    }
    
    public String getItemTypeString(){
        return this.ItemType;
    }
    
    public int getRank(){
        return this.Rank;
    }
    
    public int getGadgetId() {
		return GadgetId;
    }
    
	public int getBaseConvExp() {
		return BaseConvExp;
	}
	
	public int getMainPropDepotId() {
		return MainPropDepotId;
	}
	
	public int getAppendPropDepotId() {
		return AppendPropDepotId;
	}
	
	public int getAppendPropNum() {
		return AppendPropNum;
	}
	
	public int getSetId() {
		return SetId;
	}
	
	public int getWeaponPromoteId() {
		return WeaponPromoteId;
	}
	
	public int getWeaponBaseExp() {
		return WeaponBaseExp;
	}
	
	public int getAwakenMaterial() {
        	return AwakenMaterial;
    	}
	
	public int[] getAwakenCosts() {
		return AwakenCosts;
	}
	
	public IntSet getAddPropLevelSet() {
		return addPropLevelSet;
	}
	
	public int[] getSkillAffix() {
		return SkillAffix;
	}
	
	public WeaponProperty[] getWeaponProperties() {
		return WeaponProp;
	}
	
	public int getMaxLevel() {
		return MaxLevel;
	}
	
	public emu.grasscutter.game.inventory.ItemType getItemType() {
    	return this.itemType;
    }
    
    public emu.grasscutter.game.inventory.MaterialType getMaterialType() {
    	return this.materialType;
    }
    
    public emu.grasscutter.game.inventory.EquipType getEquipType() {
    	return this.equipType;
    }
    
    public boolean canAddRelicProp(int level) {
    	return this.addPropLevelSet != null & this.addPropLevelSet.contains(level);
    }
    
	public boolean isEquip() {
		return this.itemType == emu.grasscutter.game.inventory.ItemType.ITEM_RELIQUARY || this.itemType == emu.grasscutter.game.inventory.ItemType.ITEM_WEAPON;
	}
    
    @Override
	public void onLoad() {
    	this.itemType = emu.grasscutter.game.inventory.ItemType.getTypeByName(getItemTypeString());
    	this.materialType = emu.grasscutter.game.inventory.MaterialType.getTypeByName(getMaterialTypeString());

		if (this.itemType == emu.grasscutter.game.inventory.ItemType.ITEM_RELIQUARY) {
			this.equipType = emu.grasscutter.game.inventory.EquipType.getTypeByName(this.EquipType);
			if (this.AddPropLevels != null && this.AddPropLevels.length > 0) {
				this.addPropLevelSet = new IntOpenHashSet(this.AddPropLevels);
			}
		} else if (this.itemType == emu.grasscutter.game.inventory.ItemType.ITEM_WEAPON) {
			this.equipType = emu.grasscutter.game.inventory.EquipType.EQUIP_WEAPON;
		} else {
			this.equipType = emu.grasscutter.game.inventory.EquipType.EQUIP_NONE;
		}
		
		if (this.getWeaponProperties() != null) {
			for (WeaponProperty weaponProperty : this.getWeaponProperties()) {
				weaponProperty.onLoad();
			}
		}
    }
    
    public static class WeaponProperty {
    	private FightProperty fightProp;
        private String PropType;
        private float InitValue;
        private String Type;

        public String getPropType(){
            return this.PropType;
        }
        
        public float getInitValue(){
            return this.InitValue;
        }
        
        public String getType(){
            return this.Type;
        }

		public FightProperty getFightProp() {
			return fightProp;
		}

		public void onLoad() {
			this.fightProp = FightProperty.getPropByName(PropType);
		}
        
    }
}
