package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "ReliquarySetExcelConfigData.json")
public class ReliquarySetData extends GameResource {
    private int setId;
    private int[] setNeedNum;

    @SerializedName(value="equipAffixId", alternate={"EquipAffixId"})
    private int equipAffixId;

    @Override
    public int getId() {
        return setId;
    }

    public int[] getSetNeedNum() {
        return setNeedNum;
    }

    public int getEquipAffixId() {
        return equipAffixId;
    }

    @Override
    public void onLoad() {

    }
}
