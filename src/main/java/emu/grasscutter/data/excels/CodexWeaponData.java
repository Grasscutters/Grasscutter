package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = {"WeaponCodexExcelConfigData.json"})
public class CodexWeaponData extends GameResource {
    private int Id;
    private int weaponId;
    private int sortOrder;

    public int getSortOrder() {
        return this.sortOrder;
    }

    public int getWeaponId() {
        return this.weaponId;
    }

    public int getId() {
        return this.Id;
    }

    @Override
    public void onLoad() {
        GameData.getCodexWeaponDataIdMap().put(this.getWeaponId(), this);
    }
}
