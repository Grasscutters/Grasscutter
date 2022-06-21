package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "AvatarFlycloakExcelConfigData.json")
public class AvatarFlycloakData extends GameResource {
    private int flycloakId;
    private long nameTextMapHash;

    @Override
    public int getId() {
        return this.flycloakId;
    }

    public long getNameTextMapHash() {
        return this.nameTextMapHash;
    }

    @Override
    public void onLoad() {

    }
}
