package emu.grasscutter.game.activity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityConfigItem {
    int activityId;
    int activityType;
    int scheduleId;
    List<Integer> meetCondList;
    Date beginTime;
    Date openTime;
    Date closeTime;
    Date endTime;

    transient ActivityHandler activityHandler;

    void onLoad() {
        if (openTime == null) {
            this.openTime = beginTime;
        }

        if (closeTime == null) {
            this.closeTime = endTime;
        }
    }
}
