package emu.grasscutter.game.activity.condition.all;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.activity.*;
import emu.grasscutter.game.activity.condition.*;
import lombok.AllArgsConstructor;

/** This class is used when condition was not found */
@AllArgsConstructor
public class UnknownActivityConditionHandler extends ActivityConditionBaseHandler {
    private final ActivityConditions conditions;

    @Override
    public boolean execute(
            PlayerActivityData activityData, ActivityConfigItem activityConfig, int... params) {
        Grasscutter.getLogger().debug("Called unknown condition handler {}.", conditions.name());
        return false;
    }
}
