package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.ItemParamData;

import java.util.List;
import java.util.stream.Collectors;

@ResourceType(name = "CombineExcelConfigData.json")
public class CombineData extends GameResource {

    private int combineId;
    private int playerLevel;
    private boolean isDefaultShow;
    private int combineType;
    private int subCombineType;
    private int resultItemId;
    private int resultItemCount;
    private int scoinCost;
    private List<ItemParamData> randomItems;
    private List<ItemParamData> materialItems;
    private String recipeType;

    @Override
    public int getId() {
        return this.combineId;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        // clean data
        this.randomItems = this.randomItems.stream().filter(item -> item.getId() > 0).collect(Collectors.toList());
        this.materialItems = this.materialItems.stream().filter(item -> item.getId() > 0).collect(Collectors.toList());
    }

    public int getCombineId() {
        return this.combineId;
    }

    public int getPlayerLevel() {
        return this.playerLevel;
    }

    public boolean isDefaultShow() {
        return this.isDefaultShow;
    }

    public int getCombineType() {
        return this.combineType;
    }

    public int getSubCombineType() {
        return this.subCombineType;
    }

    public int getResultItemId() {
        return this.resultItemId;
    }

    public int getResultItemCount() {
        return this.resultItemCount;
    }

    public int getScoinCost() {
        return this.scoinCost;
    }

    public List<ItemParamData> getRandomItems() {
        return this.randomItems;
    }

    public List<ItemParamData> getMaterialItems() {
        return this.materialItems;
    }

    public String getRecipeType() {
        return this.recipeType;
    }

}

