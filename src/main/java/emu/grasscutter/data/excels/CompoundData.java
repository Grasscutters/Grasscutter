package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.ItemParamData;
import lombok.Getter;

import java.util.List;

@ResourceType(name = {"CompoundExcelConfigData.json"},loadPriority = ResourceType.LoadPriority.LOW)
@Getter
public class CompoundData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;
    private int groupID;
    private int rankLevel;
    private boolean isDefaultUnlocked;
    private int costTime;
    private int queueSize;
    private List<ItemParamData> inputVec;
    private List<ItemParamData> outputVec;
}
