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
import lombok.Getter;

@ResourceType(name = {"MaterialExcelConfigData.json",
        "WeaponExcelConfigData.json",
        "ReliquaryExcelConfigData.json",
        "HomeWorldFurnitureExcelConfigData.json"
})
public class ItemData extends GameResource {
	
	private int id;
    @Getter private int stackLimit = 1;
    @Getter private int maxUseCount;
    @Getter private int rankLevel;
    @Getter private String effectName;
    @Getter private int[] satiationParams;
    @Getter private int rank;
    @Getter private int weight;
    @Getter private int gadgetId;
    
    @Getter private int[] destroyReturnMaterial;
    @Getter private int[] destroyReturnMaterialCount;

    @Getter private List<ItemUseData> itemUse;
    
    // Food
    @Getter private String foodQuality;
    @Getter private String useTarget;
    private String[] iseParam;
    
    // String enums
    private String itemType;
    private String materialType;
    private String equipType;
    private String effectType;
    private String destroyRule;
    
    // Post load enum forms of above
    private transient MaterialType materialEnumType;
    private transient ItemType itemEnumType;
    private transient EquipType equipEnumType;
    
    // Relic
    @Getter private int mainPropDepotId;
    @Getter private int appendPropDepotId;
    @Getter private int appendPropNum;
    @Getter private int setId;
    private int[] addPropLevels;
    @Getter private int baseConvExp;
    @Getter private int maxLevel;
    
    // Weapon
    @Getter private int weaponPromoteId;
    @Getter private int weaponBaseExp;
    @Getter private int storyId;
    @Getter private int avatarPromoteId;
    @Getter private int awakenMaterial;
    @Getter private int[] awakenCosts;
    @Getter private int[] skillAffix;
    private WeaponProperty[] weaponProp;
    
    // Hash
    @Getter private String icon;
    @Getter private long nameTextMapHash;
    
    @Getter private IntSet addPropLevelSet;

    // Furniture
    @Getter private int comfort;
    @Getter private List<Integer> furnType;
    @Getter private List<Integer> furnitureGadgetID;
    @SerializedName("JFDLJGDFIGL")
    @Getter private int roomSceneId;

    @Override
	public int getId(){
        return this.id;
    }
    
    public String getMaterialTypeString(){
        return this.materialType;
    }
    
    public String[] getUseParam(){
        return this.iseParam;
    }
    
    public String getItemTypeString(){
        return this.itemType;
    }
	
	public WeaponProperty[] getWeaponProperties() {
		return weaponProp;
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
    	@Getter private FightProperty fightProp;
        @Getter private String propType;
        @Getter private float initValue;
        @Getter private String type;

		public void onLoad() {
			this.fightProp = FightProperty.getPropByName(propType);
		}
        
    }
}
