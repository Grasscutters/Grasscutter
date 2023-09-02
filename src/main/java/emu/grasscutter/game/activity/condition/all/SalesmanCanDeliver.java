package emu.grasscutter.game.activity.condition.all;

import static emu.grasscutter.game.activity.condition.ActivityConditions.NEW_ACTIVITY_COND_SALESMAN_CAN_DELIVER;

import emu.grasscutter.game.activity.*;
import emu.grasscutter.game.activity.condition.*;

@ActivityCondition(NEW_ACTIVITY_COND_SALESMAN_CAN_DELIVER)
public class SalesmanCanDeliver extends ActivityConditionBaseHandler {
    @Override
    public boolean execute(
            PlayerActivityData activityData, ActivityConfigItem activityConfig, int... params) {
        // TODO need to reverse engineer this logic.
        // This condition appears only in one condition "condId": 5003001
        // and this condition accept no params. I have no idea how to implement it
        return false;
    }
}
