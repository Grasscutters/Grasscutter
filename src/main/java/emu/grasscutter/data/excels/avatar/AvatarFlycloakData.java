package emu.grasscutter.data.excels.avatar;

import emu.grasscutter.data.*;

@ResourceType(name = "AvatarFlycloakExcelConfigData.json")
public class AvatarFlycloakData extends GameResource {
    private int flycloakId;
    private long nameTextMapHash;

    @Override
    public int getId() {
        return this.flycloakId;
    }

    public long getNameTextMapHash() {
        return nameTextMapHash;
    }

    @Override
    public void onLoad() {}
}
