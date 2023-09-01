package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.ItemParamData;
import lombok.Getter;

import java.util.List;

@ResourceType(
        name = {"CookRecipeExcelConfigData.json"},
        loadPriority = LoadPriority.LOW)
@Getter
public class CookRecipeData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    private int rankLevel;
    private boolean isDefaultUnlocked;
    private int maxProficiency;

    private List<ItemParamData> qualityOutputVec;
    private List<ItemParamData> inputVec;
}
