package emu.grasscutter.game.activity.condition.all;

import emu.grasscutter.game.activity.*;
import emu.grasscutter.game.activity.condition.*;

import static emu.grasscutter.game.activity.condition.ActivityConditions.NEW_ACTIVITY_COND_PLAYER_LEVEL_GREAT_EQUAL;

@ActivityCondition(NEW_ACTIVITY_COND_PLAYER_LEVEL_GREAT_EQUAL)
public class PlayerLevelGreatEqualActivityActivityCondition extends ActivityConditionBaseHandler {

    @Override
    public boolean execute(
            PlayerActivityData activityData, ActivityConfigItem activityConfig, int... params) {
        return activityData.getPlayer().getLevel() >= params[0];
    }
}
