package emu.grasscutter.data.excels.world;

import emu.grasscutter.data.*;

@ResourceType(name = "WorldLevelExcelConfigData.json")
public class WorldLevelData extends GameResource {
    private int level;
    private int monsterLevel;

    @Override
    public int getId() {
        return this.level;
    }

    public int getMonsterLevel() {
        return monsterLevel;
    }

    @Override
    public void onLoad() {}
}
