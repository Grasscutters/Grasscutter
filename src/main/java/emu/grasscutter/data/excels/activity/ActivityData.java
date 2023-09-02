package emu.grasscutter.data.excels.activity;

import emu.grasscutter.data.*;
import java.util.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ResourceType(
        name = "NewActivityExcelConfigData.json",
        loadPriority = ResourceType.LoadPriority.LOW)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityData extends GameResource {
    int activityId;
    String activityType;
    List<Integer> condGroupId;
    List<Integer> watcherId;
    List<ActivityWatcherData> watcherDataList;

    @Override
    public int getId() {
        return this.activityId;
    }

    @Override
    public void onLoad() {
        this.watcherDataList =
                watcherId.stream()
                        .map(item -> GameData.getActivityWatcherDataMap().get(item.intValue()))
                        .filter(Objects::nonNull)
                        .toList();
    }
}
