package emu.grasscutter.data.excels.achievement;

import emu.grasscutter.data.*;
import lombok.Getter;

@Getter
@ResourceType(name = "AchievementGoalExcelConfigData.json")
public class AchievementGoalData extends GameResource {
    private int id;
    private long nameTextMapHash;
    private int finishRewardId;
}
