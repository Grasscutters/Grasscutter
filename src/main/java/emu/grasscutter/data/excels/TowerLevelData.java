package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "TowerLevelExcelConfigData.json")
public class TowerLevelData extends GameResource {

    private int levelId;
    private int levelIndex;
    private int levelGroupId;
    private int dungeonId;

    @Override
    public int getId() {
        return this.getLevelId();
    }

    public int getLevelId() {
        return this.levelId;
    }

    public int getLevelGroupId() {
        return this.levelGroupId;
    }

    public int getLevelIndex() {
        return this.levelIndex;
    }

    public int getDungeonId() {
        return this.dungeonId;
    }
}
