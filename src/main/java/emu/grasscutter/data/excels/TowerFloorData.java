package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "TowerFloorExcelConfigData.json")
public class TowerFloorData extends GameResource {

    private int floorId;
    private int floorIndex;
    private int levelId;
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
        return floorId;
    }

    public int getFloorIndex() {
        return floorIndex;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getOverrideMonsterLevel() {
        return overrideMonsterLevel;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public int getFloorLevelConfigId() {
        return floorLevelConfigId;
    }
}
