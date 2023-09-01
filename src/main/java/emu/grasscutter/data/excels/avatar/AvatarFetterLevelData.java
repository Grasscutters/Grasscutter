package emu.grasscutter.data.excels.avatar;

import emu.grasscutter.data.*;

@ResourceType(name = "AvatarFettersLevelExcelConfigData.json")
public class AvatarFetterLevelData extends GameResource {
    private int fetterLevel;
    private int needExp;

    @Override
    public int getId() {
        return this.fetterLevel;
    }

    public int getLevel() {
        return fetterLevel;
    }

    public int getExp() {
        return needExp;
    }
}
