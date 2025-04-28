package emu.grasscutter.data.excels.reliquary;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = "ReliquarySetExcelConfigData.json")
public class ReliquarySetData extends GameResource {
    private int setId;
    @Getter private int[] setNeedNum;

    @Getter
    @SerializedName(
            value = "equipAffixId",
            alternate = {"EquipAffixId"})
    private int equipAffixId;

    @Override
    public int getId() {
        return setId;
    }

    @Override
    public void onLoad() {}
}
