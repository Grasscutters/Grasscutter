package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.ItemParamData;
import lombok.Getter;
import lombok.Setter;

@ResourceType(name = "StatuePromoteExcelConfigData.json")
public class StatuePromoteData extends GameResource {
    @Getter @Setter private int level;
    @Getter @Setter private int cityId;
    @Getter @Setter private ItemParamData[] costItems;
    @Getter @Setter private int[] rewardIdList;
    @Getter @Setter private int stamina;

    @Override
    public int getId() {
        return (cityId << 8) + level;
    }
}
