package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import lombok.*;

@Setter
@Getter
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
}
