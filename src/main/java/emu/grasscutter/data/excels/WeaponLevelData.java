package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "WeaponLevelExcelConfigData.json")
public class WeaponLevelData extends GameResource {
    private int level;
    private int[] requiredExps;

    @Override
    public int getId() {
        return this.level;
    }

    public int getLevel() {
        return this.level;
    }

    public int[] getRequiredExps() {
        return this.requiredExps;
    }
}
