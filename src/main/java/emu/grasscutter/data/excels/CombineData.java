package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
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
        randomItems =
                randomItems.stream().filter(item -> item.getId() > 0).collect(Collectors.toList());
        materialItems =
                materialItems.stream().filter(item -> item.getId() > 0).collect(Collectors.toList());
    }

    public int getCombineId() {
        return combineId;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public boolean isDefaultShow() {
        return isDefaultShow;
    }

    public int getCombineType() {
        return combineType;
    }

    public int getSubCombineType() {
        return subCombineType;
    }

    public int getResultItemId() {
        return resultItemId;
    }

    public int getResultItemCount() {
        return resultItemCount;
    }

    public int getScoinCost() {
        return scoinCost;
    }

    public List<ItemParamData> getRandomItems() {
        return randomItems;
    }

    public List<ItemParamData> getMaterialItems() {
        return materialItems;
    }

    public String getRecipeType() {
        return recipeType;
    }
}
