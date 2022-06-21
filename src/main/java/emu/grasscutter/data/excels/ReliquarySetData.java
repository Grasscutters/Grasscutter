package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "ReliquarySetExcelConfigData.json")
public class ReliquarySetData extends GameResource {
    private int setId;
    private int[] setNeedNum;
    private int EquipAffixId;
    private int disableFilter;
    private int[] containsList;

    @Override
    public int getId() {
        return this.setId;
    }

    public int[] getSetNeedNum() {
        return this.setNeedNum;
    }

    public int getEquipAffixId() {
        return this.EquipAffixId;
    }

    public int getDisableFilter() {
        return this.disableFilter;
    }

    public int[] getContainsList() {
        return this.containsList;
    }

    @Override
    public void onLoad() {

    }
}
