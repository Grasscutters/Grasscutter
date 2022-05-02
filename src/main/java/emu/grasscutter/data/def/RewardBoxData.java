package emu.grasscutter.data.def;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.RewardBoxItemData;

@ResourceType(name = "RewardPreviewExcelConfigData.json")
public class RewardBoxData extends GameResource {
    public int Id;
    public List<RewardBoxItemData> PreviewItems;

    @Override
    public int getId() {
        return Id;
    }

    public List<RewardBoxItemData> getRewardBoxItemList() {
        return PreviewItems;
    }

    @Override
    public void onLoad() {

    }
}
