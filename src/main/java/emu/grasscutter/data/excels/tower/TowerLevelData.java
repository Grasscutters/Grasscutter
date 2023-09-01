package emu.grasscutter.data.excels.tower;

import emu.grasscutter.data.*;

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
        return levelId;
    }

    public int getLevelGroupId() {
        return levelGroupId;
    }

    public int getLevelIndex() {
        return levelIndex;
    }

    public int getDungeonId() {
        return dungeonId;
    }
}
