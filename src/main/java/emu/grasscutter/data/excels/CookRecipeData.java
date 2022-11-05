package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.ItemParamData;
import lombok.Getter;

@ResourceType(name = {"CookRecipeExcelConfigData.json"}, loadPriority = LoadPriority.LOW)
@Getter
public class CookRecipeData extends GameResource {
    @Getter(onMethod = @__(@Override))
    private int id;

    private int rankLevel;
    private boolean isDefaultUnlocked;
    private int maxProficiency;

    private List<ItemParamData> qualityOutputVec;
    private List<ItemParamData> inputVec;
}
