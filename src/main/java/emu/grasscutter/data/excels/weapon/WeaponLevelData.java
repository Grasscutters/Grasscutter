package emu.grasscutter.data.excels.weapon;

import emu.grasscutter.data.*;

@ResourceType(name = "WeaponLevelExcelConfigData.json")
public class WeaponLevelData extends GameResource {
    private int level;
    private int[] requiredExps;

    @Override
    public int getId() {
        return this.level;
    }

    public int getLevel() {
        return level;
    }

    public int[] getRequiredExps() {
        return requiredExps;
    }
}
