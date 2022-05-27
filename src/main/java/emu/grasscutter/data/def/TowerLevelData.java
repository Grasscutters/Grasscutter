package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "TowerLevelExcelConfigData.json")
public class TowerLevelData extends GameResource {

    private int ID;
    private int LevelId;
    private int LevelIndex;
    private int DungeonId;

    @Override
    public int getId() {
        return this.ID;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getLevelId() {
        return LevelId;
    }

    public void setLevelId(int levelId) {
        LevelId = levelId;
    }

    public int getLevelIndex() {
        return LevelIndex;
    }

    public void setLevelIndex(int levelIndex) {
        LevelIndex = levelIndex;
    }

    public int getDungeonId() {
        return DungeonId;
    }

    public void setDungeonId(int dungeonId) {
        DungeonId = dungeonId;
    }
}
