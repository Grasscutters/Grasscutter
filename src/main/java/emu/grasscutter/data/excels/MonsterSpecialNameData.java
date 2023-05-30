package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.ResourceType.LoadPriority;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ResourceType(name = "MonsterSpecialNameExcelConfigData.json", loadPriority = LoadPriority.HIGH)
@EqualsAndHashCode(callSuper=false)
@Data
public class MonsterSpecialNameData extends GameResource {
    @SerializedName(value = "specialNameId", alternate={"specialNameID"})
    private int specialNameId;
    @SerializedName(value = "specialNameLabId", alternate={"specialNameLabID"})
    private int specialNameLabId;
    private long specialNameTextMapHash;

    @Override
    public int getId() {
        return specialNameId;
    }
}