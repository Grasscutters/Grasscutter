package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

@ResourceType(name = "PlayerLevelExcelConfigData.json")
public class PlayerLevelData extends GameResource {
	private int level;
	private int exp;
	private int rewardId;
	private int unlockWorldLevel;
	
	@Override
	public int getId() {
		return this.level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getExp() {
		return exp;
	}
	
	public int getRewardId() {
		return rewardId;
	}
	
	public int getUnlockWorldLevel() {
		return unlockWorldLevel;
	}
}
