package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.ItemParamData;
import lombok.Getter;

@ResourceType(name = {"CookRecipeExcelConfigData.json"}, loadPriority = LoadPriority.LOW)
public class CookRecipeData extends GameResource {
    private int id;

    @Getter private int rankLevel;
    @Getter boolean isDefaultUnlocked;
    @Getter int maxProficiency;

    @Getter List<ItemParamData> qualityOutputVec;
    @Getter List<ItemParamData> inputVec;

    @Override
    public int getId() {
        return this.id;
    }
    @Override
    public void onLoad() {
    }
}
