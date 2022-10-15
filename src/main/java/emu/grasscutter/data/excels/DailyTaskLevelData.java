package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ResourceType(name = {"DailyTaskLevelExcelConfigData.json"})
public class DailyTaskLevelData extends GameResource {
    int id;
    int minPlayerLevel;
    int maxPlayerLevel;
    int groupReviseLevel;
    int scoreDropId;
    int scorePreviewRewardId;

    @Override
    public int getId() {
        return id;
    }
}
