package emu.grasscutter.data.excels.world;

import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = "WorldLevelExcelConfigData.json")
public class WorldLevelData extends GameResource {
    private int level;
    @Getter private int monsterLevel;

    @Override
    public int getId() {
        return this.level;
    }

    @Override
    public void onLoad() {}
}
