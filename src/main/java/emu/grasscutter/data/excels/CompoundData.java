package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.ItemParamData;
import lombok.Getter;

import java.util.List;

@ResourceType(name = {"CompoundExcelConfigData.json"},loadPriority = ResourceType.LoadPriority.LOW)
public class CompoundData extends GameResource {
    private int id;

    @Override
    public int getId(){return this.id;}
    @Getter private int groupId;
    @Getter private int rankLevel;
    @Getter private boolean isDefaultUnlocked;
    @Getter private int costTime;
    @Getter private int queueSize;
    @Getter private List<ItemParamData> inputVec;
    @Getter private List<ItemParamData> outputVec;

    @Override
    public void onLoad(){}
}
