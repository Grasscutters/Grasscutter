package emu.grasscutter.data.excels.tower;

import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = "TowerFloorExcelConfigData.json")
@Getter
public class TowerFloorData extends GameResource {
    private int floorId;
    private int floorIndex;
    private int levelGroupId;
    private int overrideMonsterLevel;
    private int teamNum;
    private int floorLevelConfigId;

    @Override
    public int getId() {
        return this.floorId;
    }
}
