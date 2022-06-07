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
		return setId;
	}
	
	public int[] getSetNeedNum() {
		return setNeedNum;
	}

	public int getEquipAffixId() {
		return EquipAffixId;
	}

	public int getDisableFilter() {
		return disableFilter;
	}

	public int[] getContainsList() {
		return containsList;
	}

	@Override
	public void onLoad() {

	}
}
