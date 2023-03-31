package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import lombok.Getter;

@ResourceType(name = "MonsterDescribeExcelConfigData.json", loadPriority = LoadPriority.HIGH)
@Getter
public class MonsterDescribeData extends GameResource {
    @Getter(onMethod_ = @Override)
    private int id;
    private long nameTextMapHash;
    private int titleID;
    private int specialNameLabID;
}
