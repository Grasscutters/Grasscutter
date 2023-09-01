package emu.grasscutter.data.excels.avatar;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;

@ResourceType(name = "AvatarCostumeExcelConfigData.json")
public class AvatarCostumeData extends GameResource {
    @SerializedName(value = "skinId", alternate = "costumeId")
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
