package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import java.util.List;

@ResourceType(name = "RewardExcelConfigData.json")
public class RewardData extends GameResource {
    public int rewardId;
    public List<ItemParamData> rewardItemList;

    @Override
    public int getId() {
        return rewardId;
    }

    public List<ItemParamData> getRewardItemList() {
        return rewardItemList;
    }

    @Override
    public void onLoad() {
        rewardItemList = rewardItemList.stream().filter(i -> i.getId() > 0).toList();
    }
}
