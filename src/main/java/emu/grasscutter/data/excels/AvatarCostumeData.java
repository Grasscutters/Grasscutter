package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "AvatarCostumeExcelConfigData.json")
public class AvatarCostumeData extends GameResource {
    private int skinId;
    private int itemId;
    private int characterId;
    private int quality;

    @Override
    public int getId() {
        return this.skinId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getQuality() {
        return quality;
    }

    @Override
    public void onLoad() {
        GameData.getAvatarCostumeDataItemIdMap().put(this.getItemId(), this);
    }
}
