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
        return this.level;
    }

    public int getExp() {
        return this.exp;
    }

    public int getRewardId() {
        return this.rewardId;
    }

    public int getUnlockWorldLevel() {
        return this.unlockWorldLevel;
    }
}
