package emu.grasscutter.data.excels.monster;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ResourceType(name = "MonsterSpecialNameExcelConfigData.json", loadPriority = LoadPriority.HIGH)
@EqualsAndHashCode(callSuper = false)
@Data
public class MonsterSpecialNameData extends GameResource {
    private int specialNameId;
    private int specialNameLabId;
    private long specialNameTextMapHash;

    @Override
    public int getId() {
        return specialNameId;
    }
}
