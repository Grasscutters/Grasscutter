package emu.grasscutter.data.def;

import emu.grasscutter.data.GenshinResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "AvatarFettersLevelExcelConfigData.json")
public class AvatarFetterLevelData extends GenshinResource {
	private int FetterLevel;
	private int NeedExp;
	
	@Override
	public int getId() {
		return this.FetterLevel;
	}
	
	public int getLevel() {
		return FetterLevel;
	}
	
	public int getExp() {
		return NeedExp;
	}
}
