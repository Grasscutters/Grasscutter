package emu.grasscutter.data.excels.avatar;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import lombok.*;

@Data
@ResourceType(name = "AvatarReplaceCostumeExcelConfigData.json")
@EqualsAndHashCode(callSuper = false)
public class AvatarReplaceCostumeData extends GameResource {
    private int avatarId;

    @SerializedName(
            value = "costumeId",
            alternate = {"MGLCOPOIJIC", "BDBMOBGKIAP"})
    private int costumeId;

    @Override
    public int getId() {
        return costumeId;
    }
}
