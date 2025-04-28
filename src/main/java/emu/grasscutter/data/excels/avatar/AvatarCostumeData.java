package emu.grasscutter.data.excels.avatar;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = "AvatarCostumeExcelConfigData.json")
public class AvatarCostumeData extends GameResource {
    @SerializedName(value = "skinId", alternate = "costumeId")
    private int skinId;

    @Getter private int itemId;
    @Getter private int characterId;
    @Getter private int quality;

    @Override
    public int getId() {
        return this.skinId;
    }

    @Override
    public void onLoad() {
        GameData.getAvatarCostumeDataItemIdMap().put(this.getItemId(), this);
    }
}
