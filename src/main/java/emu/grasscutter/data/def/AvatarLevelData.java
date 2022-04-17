package emu.grasscutter.data.def;

import emu.grasscutter.data.GenshinResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "AvatarLevelExcelConfigData.json")
public class AvatarLevelData extends GenshinResource {
	private int Level;
	private int Exp;
	
	@Override
	public int getId() {
		return this.Level;
	}
	
	public int getLevel() {
		return Level;
	}
	
	public int getExp() {
		return Exp;
	}
}
