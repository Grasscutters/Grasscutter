package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "WeaponLevelExcelConfigData.json")
public class WeaponLevelData extends GameResource {
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
