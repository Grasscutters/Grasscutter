package emu.grasscutter.game.activity;

import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

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
