package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import lombok.Getter;

@ResourceType(name = "PlayerLevelExcelConfigData.json")
@Getter
public class PlayerLevelData extends GameResource {
    private int level;
    private int exp;
    private int rewardId;
    private int expeditionLimitAdd = 0;
    private int unlockWorldLevel;
    private long unlockDescTextMapHash;

    @Override
    public int getId() {
        return this.level;
    }
}
