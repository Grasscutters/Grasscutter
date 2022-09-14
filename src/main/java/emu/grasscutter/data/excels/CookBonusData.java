package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.ItemParamData;

@ResourceType(name = {"CookBonusExcelConfigData.json"}, loadPriority = LoadPriority.LOW)
public class CookBonusData extends GameResource {
    private int avatarId;
    private int recipeId;
    private int[] paramVec;
    private int[] complexParamVec;

    @Override
	public int getId() {
		return this.avatarId;
	}

    public int getAvatarId() {
        return avatarId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int[] getParamVec() {
        return paramVec;
    }

    public int[] getComplexParamVec() {
        return complexParamVec;
    }

    public int getReplacementItemId() {
        return this.paramVec[0];
    }

    @Override
    public void onLoad() {
    }
}
