package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "TowerFloorExcelConfigData.json")
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

    @Override
    public void onLoad() {
        super.onLoad();
    }

    public int getFloorId() {
        return this.floorId;
    }

    public int getFloorIndex() {
        return this.floorIndex;
    }

    public int getLevelGroupId() {
        return this.levelGroupId;
    }

    public int getOverrideMonsterLevel() {
        return this.overrideMonsterLevel;
    }

    public int getTeamNum() {
        return this.teamNum;
    }

    public int getFloorLevelConfigId() {
        return this.floorLevelConfigId;
    }
}
