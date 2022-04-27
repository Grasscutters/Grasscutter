package emu.grasscutter.data.def;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.RewardItemData;

@ResourceType(name = "RewardExcelConfigData.json")
public class RewardData extends GameResource {
    public int RewardId;
    public List<RewardItemData> RewardItemList;

    @Override
	public int getId() {
		return RewardId;
	}

    public List<RewardItemData> getRewardItemList() {
        return RewardItemList;
    }

    @Override
    public void onLoad() {

    }
}
