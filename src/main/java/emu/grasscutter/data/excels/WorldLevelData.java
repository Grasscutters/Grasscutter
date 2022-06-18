package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "WorldLevelExcelConfigData.json")
public class WorldLevelData extends GameResource {
    private int level;
    private int monsterLevel;

    @Override
    public int getId() {
        return this.level;
    }

    public int getMonsterLevel() {
        return this.monsterLevel;
    }

    @Override
    public void onLoad() {

    }
}
