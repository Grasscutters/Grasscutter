package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.ItemParamData;

@ResourceType(name = "StatuePromoteExcelConfigData.json")
public class StatuePromoteData extends GameResource {
    private int level;
    private int cityId;
    private ItemParamData[] costItems;
    private int[] rewardIdList;
    private int stamina;

    @Override
    public int getId() {
        return (cityId << 8) + level;
    }

    public int getStatueLevel() {
        return level;
    }

    public int getCityId() {
        return cityId;
    }

    public ItemParamData[] getCostItems() {
        return costItems;
    }

    public int[] getRewardIdList() {
        return rewardIdList;
    }

    public int getStamina() {
        return stamina;
    }
}
