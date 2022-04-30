package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "WorldLevelExcelConfigData.json")
public class WorldLevelData extends GameResource {
	private int Level;
	private int MonsterLevel;
	    
	@Override
	public int getId() {
		return this.Level;
	}

	public int getMonsterLevel() {
		return MonsterLevel;
	}

	@Override
	public void onLoad() {
		
	}
}
