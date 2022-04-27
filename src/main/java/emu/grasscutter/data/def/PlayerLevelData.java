package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "PlayerLevelExcelConfigData.json")
public class PlayerLevelData extends GameResource {
	private int Level;
	private int Exp;
	private int RewardId;
	private int UnlockWorldLevel;
	
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
	
	public int getRewardId() {
		return RewardId;
	}
	
	public int getUnlockWorldLevel() {
		return UnlockWorldLevel;
	}
}
