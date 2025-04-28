package emu.grasscutter.data.excels.weapon;

import emu.grasscutter.data.*;
import lombok.Getter;

@Getter
@ResourceType(name = "WeaponLevelExcelConfigData.json")
public class WeaponLevelData extends GameResource {
    private int level;
    private int[] requiredExps;

    @Override
    public int getId() {
        return this.level;
    }
}
