package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "AvatarCostumeExcelConfigData.json")
public class AvatarCostumeData extends GameResource {
    private int costumeId;
    private int itemId;
    private int avatarId;

    @Override
    public int getId() {
        return this.costumeId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getAvatarId() {
        return this.avatarId;
    }

    @Override
    public void onLoad() {
        GameData.getAvatarCostumeDataItemIdMap().put(this.getItemId(), this);
    }
}
