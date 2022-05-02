package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "ReliquarySetExcelConfigData.json")
public class ReliquarySetData extends GameResource {
	private int SetId;
    private int[] SetNeedNum;
    private int EquipAffixId;
    private int DisableFilter;
    private int[] ContainsList;
	
	@Override
	public int getId() {
		return SetId;
	}
	
	public int[] getSetNeedNum() {
		return SetNeedNum;
	}

	public int getEquipAffixId() {
		return EquipAffixId;
	}

	public int getDisableFilter() {
		return DisableFilter;
	}

	public int[] getContainsList() {
		return ContainsList;
	}

	@Override
	public void onLoad() {

	}
}
