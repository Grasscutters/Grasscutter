package emu.grasscutter.data.def;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.ItemParamData;

@ResourceType(name = "RewardExcelConfigData.json")
public class RewardData extends GameResource {
    public int RewardId;
    public List<ItemParamData> RewardItemList;

    @Override
	public int getId() {
		return RewardId;
	}

    public List<ItemParamData> getRewardItemList() {
        return RewardItemList;
    }

    @Override
    public void onLoad() {
    	RewardItemList = RewardItemList.stream().filter(i -> i.getId() > 0).toList();
    }
}
