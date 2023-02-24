package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.Getter;

@Getter
@ResourceType(name = "AchievementGoalExcelConfigData.json")
public class AchievementGoalData extends GameResource {
    private int id;
    private long nameTextMapHash;
    private int finishRewardId;
}
