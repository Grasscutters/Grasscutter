package emu.grasscutter.data.def;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = {"WeaponCodexExcelConfigData.json"})
public class CodexWeaponData extends GameResource {
    private int Id;
    private int WeaponId;
    private int SortOrder;

    public int getSortOrder() {
        return SortOrder;
    }

    public int getWeaponId() {
        return WeaponId;
    }

    public int getId() {
        return Id;
    }

    @Override
    public void onLoad() {
        GameData.getCodexWeaponDataIdMap().put(this.getWeaponId(), this);
    }
}
