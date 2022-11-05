package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.ItemParamData;
import lombok.Getter;

@ResourceType(name = {"ForgeExcelConfigData.json"}, loadPriority = LoadPriority.HIGHEST)
@Getter
public class ForgeData extends GameResource {
    @Getter(onMethod = @__(@Override))
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
