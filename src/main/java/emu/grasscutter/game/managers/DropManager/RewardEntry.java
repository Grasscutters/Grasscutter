package emu.grasscutter.game.managers.DropManager;

import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.common.ItemParamStringData;

import java.util.Arrays;

public class RewardEntry {
    private int sceneID;
    private ItemParamStringData[] rewardItemList;

    public int getId() {
        return this.sceneID;
    }

    public ItemParamData[] getPreviewItems() {
        if (this.rewardItemList != null && this.rewardItemList.length > 0) {
            return Arrays.stream(this.rewardItemList)
                    .filter(d -> d.getId() > 0 && d.getCount() != null && !d.getCount().isEmpty())
                    .map(ItemParamStringData::toItemParamData)
                    .toArray(size -> new ItemParamData[size]);
        } else {
            return new ItemParamData[0];
        }
    }
}
