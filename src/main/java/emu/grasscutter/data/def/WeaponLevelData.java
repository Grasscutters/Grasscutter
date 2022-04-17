package emu.grasscutter.data.def;

import emu.grasscutter.data.GenshinResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "WeaponLevelExcelConfigData.json")
public class WeaponLevelData extends GenshinResource {
	private int Level;
	private int[] RequiredExps;
	
	@Override
	public int getId() {
		return this.Level;
	}
	
	public int getLevel() {
		return Level;
	}
	
	public int[] getRequiredExps() {
		return RequiredExps;
	}
}
