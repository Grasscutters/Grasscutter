package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ResourceType(name = "AvatarReplaceCostumeExcelConfigData.json")
@EqualsAndHashCode(callSuper=false)
public class AvatarReplaceCostumeData extends GameResource {
	private int avatarId;
    @SerializedName(value = "costumeId", alternate={"MGLCOPOIJIC", "BDBMOBGKIAP"})
    private int costumeId;

    @Override
    public int getId() {
        return costumeId;
    }
}
