package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import java.util.List;
import lombok.Getter;

@ResourceType(
        name = {"CompoundExcelConfigData.json"},
        loadPriority = ResourceType.LoadPriority.LOW)
@Getter
public class CompoundData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;

    @SerializedName("groupID")
    private int groupId;

    private int rankLevel;
    private boolean isDefaultUnlocked;
    private int costTime;
    private int queueSize;
    private List<ItemParamData> inputVec;
    private List<ItemParamData> outputVec;
}
