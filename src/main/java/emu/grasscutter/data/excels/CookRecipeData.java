package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;

@ResourceType(name = {"CookRecipeExcelConfigData.json"}, loadPriority = LoadPriority.LOW)
public class CookRecipeData extends GameResource {
    private int id;

    private int rankLevel;
    private boolean isDefaultUnlocked;
    private int maxProficiency;

    private List<ItemParamData> qualityOutputVec;
    private List<ItemParamData> inputVec;

    @Override
	public int getId() {
		return this.id;
	}

    public int getRankLevel() {
        return this.rankLevel;
    }

    public boolean isDefaultUnlocked() {
        return this.isDefaultUnlocked;
    }

    public int getMaxProficiency() {
        return this.maxProficiency;
    }

    public List<ItemParamData> getQualityOutputVec() {
        return this.qualityOutputVec;
    }

    public List<ItemParamData> getInputVec() {
        return this.inputVec;
    }

    @Override
    public void onLoad() {
    }
}
