package emu.grasscutter.game.activity.condition.all;

import emu.grasscutter.game.activity.ActivityConfigItem;
import emu.grasscutter.game.activity.PlayerActivityData;
import emu.grasscutter.game.activity.condition.ActivityCondition;
import emu.grasscutter.game.activity.condition.ActivityConditionBaseHandler;

import static emu.grasscutter.game.activity.condition.ActivityConditions.NEW_ACTIVITY_COND_DAYS_LESS;

@ActivityCondition(NEW_ACTIVITY_COND_DAYS_LESS)
public class DayLess extends ActivityConditionBaseHandler {
    @Override
    public boolean execute(PlayerActivityData activityData, ActivityConfigItem activityConfig, int... params) {
        return true; //TODO implement this and add possibility to always return true
    }
}
