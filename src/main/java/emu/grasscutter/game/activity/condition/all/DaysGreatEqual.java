package emu.grasscutter.game.activity.condition.all;

import static emu.grasscutter.game.activity.condition.ActivityConditions.NEW_ACTIVITY_COND_DAYS_GREAT_EQUAL;

import emu.grasscutter.game.activity.*;
import emu.grasscutter.game.activity.condition.*;
import java.util.Date;

@ActivityCondition(NEW_ACTIVITY_COND_DAYS_GREAT_EQUAL)
public class DaysGreatEqual extends ActivityConditionBaseHandler {
    @Override
    public boolean execute(
            PlayerActivityData activityData, ActivityConfigItem activityConfig, int... params) {
        Date activityBeginTime = activityConfig.getBeginTime();
        long timeDiff = System.currentTimeMillis() - activityBeginTime.getTime();
        int days = (int) (timeDiff / (1000 * 60 * 60 * 24L));
        return days + 1 >= params[0];
    }
}
