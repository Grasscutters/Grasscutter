package emu.grasscutter.data.excels;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.ItemUseData;
import emu.grasscutter.game.inventory.*;
import emu.grasscutter.game.props.FightProperty;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

@ResourceType(name = {"MaterialExcelConfigData.json",
        "WeaponExcelConfigData.json",
        "ReliquaryExcelConfigData.json",
        "HomeWorldFurnitureExcelConfigData.json"
})
public class ItemData extends GameResource {
	
	private int id;
    private int stackLimit = 1;
    private int maxUseCount;
    private int rankLevel;
    private String effectName;
    private int[] satiationParams;
    private int rank;
    private int weight;
    private int gadgetId;
    
    private int[] destroyReturnMaterial;
    private int[] destroyReturnMaterialCount;

    private List<ItemUseData> itemUse;
    
    // Food
    private String foodQuality;
    private String useTarget;
    private String[] iseParam;
    
    // String enums
    private String itemType;
    private String materialType;
    private String equipType;
    private String effectType;
    private String destroyRule;
    
    // Relic
    private int mainPropDepotId;
    private int appendPropDepotId;
    private int appendPropNum;
    private int setId;
    private int[] addPropLevels;
    private int baseConvExp;
    private int maxLevel;
    
    // Weapon
    private int weaponPromoteId;
    private int weaponBaseExp;
    private int storyId;
    private int avatarPromoteId;
    private int awakenMaterial;
    private int[] awakenCosts;
    private int[] skillAffix;
    private WeaponProperty[] weaponProp;
    
    // Hash
    private String icon;
    private long nameTextMapHash;
    
    // Post load
    private transient MaterialType materialEnumType;
    private transient ItemType itemEnumType;
    private transient EquipType equipEnumType;
    
    private IntSet addPropLevelSet;

    // Furniture
    private int comfort;
    private List<Integer> furnType;
    private List<Integer> furnitureGadgetID;
    @SerializedName("JFDLJGDFIGL")
    private int roomSceneId;

    @Override
	public int getId(){
        return this.id;
    }
    
    public String getMaterialTypeString(){
        return this.materialType;
    }
    
    public int getStackLimit(){
        return this.stackLimit;
    }
    
    public int getMaxUseCount(){
        return this.maxUseCount;
    }
    
    public String getUseTarget(){
        return this.useTarget;
    }
    
    public String[] getUseParam(){
        return this.iseParam;
    }
    
    public int getRankLevel(){
        return this.rankLevel;
    }
    
    public String getFoodQuality(){
        return this.foodQuality;
    }
    
    public String getEffectName(){
        return this.effectName;
    }
    
    public int[] getSatiationParams(){
        return this.satiationParams;
    }
    
    public int[] getDestroyReturnMaterial(){
        return this.destroyReturnMaterial;
    }
    
    public int[] getDestroyReturnMaterialCount(){
        return this.destroyReturnMaterialCount;
    }

    public List<ItemUseData> getItemUse() {
        return itemUse;
    }

    public long getNameTextMapHash(){
        return this.nameTextMapHash;
    }
    
    public String getIcon(){
        return this.icon;
    }
    
    public String getItemTypeString(){
        return this.itemType;
    }
    
    public int getRank(){
        return this.rank;
    }
    
    public int getGadgetId() {
		return gadgetId;
    }
    
	public int getBaseConvExp() {
		return baseConvExp;
	}
	
	public int getMainPropDepotId() {
		return mainPropDepotId;
	}
	
	public int getAppendPropDepotId() {
		return appendPropDepotId;
	}
	
	public int getAppendPropNum() {
		return appendPropNum;
	}
	
	public int getSetId() {
		return setId;
	}
	
	public int getWeaponPromoteId() {
		return weaponPromoteId;
	}
	
	public int getWeaponBaseExp() {
		return weaponBaseExp;
	}
	
	public int getAwakenMaterial() {
        	return awakenMaterial;
    	}
	
	public int[] getAwakenCosts() {
		return awakenCosts;
	}
	
	public IntSet getAddPropLevelSet() {
		return addPropLevelSet;
	}
	
	public int[] getSkillAffix() {
		return skillAffix;
	}
	
	public WeaponProperty[] getWeaponProperties() {
		return weaponProp;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}

    public int getComfort() {
        return comfort;
    }

    public List<Integer> getFurnType() {
        return furnType;
    }

    public List<Integer> getFurnitureGadgetID() {
        return furnitureGadgetID;
    }

    public int getRoomSceneId() {
        return roomSceneId;
    }

    public ItemType getItemType() {
    	return this.itemEnumType;
    }
    
    public MaterialType getMaterialType() {
    	return this.materialEnumType;
    }
    
    public EquipType getEquipType() {
    	return this.equipEnumType;
    }
    
    public boolean canAddRelicProp(int level) {
    	return this.addPropLevelSet != null && this.addPropLevelSet.contains(level);
    }
    
	public boolean isEquip() {
		return this.itemEnumType == ItemType.ITEM_RELIQUARY || this.itemEnumType == ItemType.ITEM_WEAPON;
	}
    
    @Override
	public void onLoad() {
    	this.itemEnumType = ItemType.getTypeByName(getItemTypeString());
    	this.materialEnumType = MaterialType.getTypeByName(getMaterialTypeString());

		if (this.itemEnumType == ItemType.ITEM_RELIQUARY) {
			this.equipEnumType = EquipType.getTypeByName(this.equipType);
			if (this.addPropLevels != null && this.addPropLevels.length > 0) {
				this.addPropLevelSet = new IntOpenHashSet(this.addPropLevels);
			}
		} else if (this.itemEnumType == ItemType.ITEM_WEAPON) {
			this.equipEnumType = EquipType.EQUIP_WEAPON;
		} else {
			this.equipEnumType = EquipType.EQUIP_NONE;
		}
		
		if (this.getWeaponProperties() != null) {
			for (WeaponProperty weaponProperty : this.getWeaponProperties()) {
				weaponProperty.onLoad();
			}
		}

        if(this.getFurnType() != null){
            this.furnType = this.furnType.stream().filter(x -> x > 0).toList();
        }
        if(this.getFurnitureGadgetID() != null){
            this.furnitureGadgetID = this.furnitureGadgetID.stream().filter(x -> x > 0).toList();
        }
    }
    
    public static class WeaponProperty {
    	private FightProperty fightProp;
        private String propType;
        private float initValue;
        private String type;

        public String getPropType(){
            return this.propType;
        }
        
        public float getInitValue(){
            return this.initValue;
        }
        
        public String getType(){
            return this.type;
        }

		public FightProperty getFightProp() {
			return fightProp;
		}

		public void onLoad() {
			this.fightProp = FightProperty.getPropByName(propType);
		}
        
    }
}
