package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import java.util.List;
import lombok.Getter;

@ResourceType(name = "RewardExcelConfigData.json")
public class RewardData extends GameResource {
    public int rewardId;
    @Getter public List<ItemParamData> rewardItemList;

    @Override
    public int getId() {
        return rewardId;
    }

    @Override
    public void onLoad() {
        rewardItemList = rewardItemList.stream().filter(i -> i.getId() > 0).toList();
    }
}
