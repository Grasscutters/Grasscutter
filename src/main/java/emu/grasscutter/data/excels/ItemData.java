package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemUseData;
import emu.grasscutter.game.home.SpecialFurnitureType;
import emu.grasscutter.game.inventory.*;
import emu.grasscutter.game.props.*;
import emu.grasscutter.game.props.ItemUseAction.ItemUseAction;
import it.unimi.dsi.fastutil.ints.*;
import java.util.*;
import lombok.Getter;

@ResourceType(
        name = {
            "MaterialExcelConfigData.json",
            "WeaponExcelConfigData.json",
            "ReliquaryExcelConfigData.json",
            "HomeWorldFurnitureExcelConfigData.json"
        })
@Getter
public class ItemData extends GameResource {
    // Main
    @Getter(onMethod_ = @Override)
    private int id;

    private int stackLimit = 1;
    private int maxUseCount;
    private int rankLevel;
    private String effectName;
    private int rank;
    private int weight;
    private int gadgetId;
    private String icon;

    private int[] destroyReturnMaterial;
    private int[] destroyReturnMaterialCount;

    // Enums
    private ItemType itemType = ItemType.ITEM_NONE;
    private MaterialType materialType = MaterialType.MATERIAL_NONE;
    private EquipType equipType = EquipType.EQUIP_NONE;
    private String effectType;
    private String destroyRule;

    // Food
    private String foodQuality;
    private int[] satiationParams;

    // Usable item
    private ItemUseTarget useTarget = ItemUseTarget.ITEM_USE_TARGET_NONE;
    private List<ItemUseData> itemUse;
    private List<ItemUseAction> itemUseActions;
    private boolean useOnGain = false;

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
    private long nameTextMapHash;

    // Furniture
    private int comfort;
    private List<Integer> furnType;
    private List<Integer> furnitureGadgetID;
    private SpecialFurnitureType specialFurnitureType = SpecialFurnitureType.NOT_SPECIAL;

    @SerializedName(
            value = "roomSceneId",
            alternate = {
                "BMEPAMCNABE",
                "DANFGGLKLNO",
                "JFDLJGDFIGL",
                "OHIANNAEEAK",
                "MFGACDIOHGF",
                "roomSceneID"
            })
    private int roomSceneId;

    // Custom
    private transient IntSet addPropLevelSet;

    public WeaponProperty[] getWeaponProperties() {
        return this.weaponProp;
    }

    public boolean canAddRelicProp(int level) {
        return this.addPropLevelSet != null && this.addPropLevelSet.contains(level);
    }

    public boolean isEquip() {
        return this.itemType == ItemType.ITEM_RELIQUARY || this.itemType == ItemType.ITEM_WEAPON;
    }

    @Override
    public void onLoad() {
        if (this.itemType == ItemType.ITEM_RELIQUARY) {
            if (this.addPropLevels != null && this.addPropLevels.length > 0) {
                this.addPropLevelSet = new IntOpenHashSet(this.addPropLevels);
            }
        } else if (this.itemType == ItemType.ITEM_WEAPON) {
            this.equipType = EquipType.EQUIP_WEAPON;
        } else {
            this.equipType = EquipType.EQUIP_NONE;
        }

        if (this.weaponProp != null) {
            this.weaponProp =
                    Arrays.stream(this.weaponProp)
                            .filter(prop -> prop.getPropType() != null)
                            .toArray(WeaponProperty[]::new);
        }

        if (this.getFurnType() != null) {
            this.furnType = this.furnType.stream().filter(x -> x > 0).toList();
        }
        if (this.getFurnitureGadgetID() != null) {
            this.furnitureGadgetID = this.furnitureGadgetID.stream().filter(x -> x > 0).toList();
        }

        // Prevent material type from being null
        this.materialType = this.materialType == null ? MaterialType.MATERIAL_NONE : this.materialType;

        if (this.itemUse != null && !this.itemUse.isEmpty()) {
            this.itemUseActions =
                    this.itemUse.stream()
                            .filter(x -> x.getUseOp() != ItemUseOp.ITEM_USE_NONE)
                            .map(ItemUseAction::fromItemUseData)
                            .filter(Objects::nonNull)
                            .toList();
        }
    }

    @Getter
    public static class WeaponProperty {
        private FightProperty propType;
        private float initValue;
        private String type;
    }
}
