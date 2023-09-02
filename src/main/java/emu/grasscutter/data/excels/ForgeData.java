package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.ItemParamData;
import java.util.List;
import lombok.Getter;

@ResourceType(
        name = {"ForgeExcelConfigData.json"},
        loadPriority = LoadPriority.HIGHEST)
@Getter
public class ForgeData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    private int playerLevel;
    private int forgeType;
    private int showItemId;
    private int resultItemId;
    private int resultItemCount;
    private int forgeTime;
    private int queueNum;
    private int scoinCost;
    private int priority;
    private int forgePoint;
    private List<ItemParamData> materialItems;
}
