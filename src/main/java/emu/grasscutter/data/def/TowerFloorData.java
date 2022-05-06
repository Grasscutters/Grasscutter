package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "TowerFloorExcelConfigData.json")
public class TowerFloorData extends GameResource {

    private int FloorId;
    private int FloorIndex;
    private int LevelId;
    private int OverrideMonsterLevel;
    private int TeamNum;
    private int FloorLevelConfigId;

    @Override
    public int getId() {
        return this.FloorId;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    public int getFloorId() {
        return FloorId;
    }

    public void setFloorId(int floorId) {
        FloorId = floorId;
    }

    public int getFloorIndex() {
        return FloorIndex;
    }

    public void setFloorIndex(int floorIndex) {
        FloorIndex = floorIndex;
    }

    public int getLevelId() {
        return LevelId;
    }

    public void setLevelId(int levelId) {
        LevelId = levelId;
    }

    public int getOverrideMonsterLevel() {
        return OverrideMonsterLevel;
    }

    public void setOverrideMonsterLevel(int overrideMonsterLevel) {
        OverrideMonsterLevel = overrideMonsterLevel;
    }

    public int getTeamNum() {
        return TeamNum;
    }

    public void setTeamNum(int teamNum) {
        TeamNum = teamNum;
    }

    public int getFloorLevelConfigId() {
        return FloorLevelConfigId;
    }

    public void setFloorLevelConfigId(int floorLevelConfigId) {
        FloorLevelConfigId = floorLevelConfigId;
    }
}
