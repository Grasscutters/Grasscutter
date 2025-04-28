package emu.grasscutter.data.excels.codex;

import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = {"WeaponCodexExcelConfigData.json"})
public class CodexWeaponData extends GameResource {
    @Getter private int Id;
    @Getter private int weaponId;
    private int gadgetId;
    @Getter private int sortOrder;

    public int getGadgetId() {
        return weaponId;
    }

    @Override
    public void onLoad() {
        GameData.getCodexWeaponDataIdMap().put(this.getWeaponId(), this);
    }
}
