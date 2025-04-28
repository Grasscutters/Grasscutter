package emu.grasscutter.data.excels.avatar;

import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = "AvatarFlycloakExcelConfigData.json")
public class AvatarFlycloakData extends GameResource {
    private int flycloakId;
    @Getter private long nameTextMapHash;

    @Override
    public int getId() {
        return this.flycloakId;
    }

    @Override
    public void onLoad() {}
}
