package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@ResourceType(name = "CombineExcelConfigData.json")
public class CombineData extends GameResource {

    @Getter private int combineId;
    @Getter private int playerLevel;
    private boolean isDefaultShow;
    @Getter private int combineType;
    @Getter private int subCombineType;
    @Getter private int resultItemId;
    @Getter private int resultItemCount;
    @Getter private int scoinCost;
    @Getter private List<ItemParamData> randomItems;
    @Getter private List<ItemParamData> materialItems;
    @Getter private String recipeType;

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

    public boolean isDefaultShow() {
        return isDefaultShow;
    }
}
