package emu.grasscutter.game.activity.condition.all;

import static emu.grasscutter.game.activity.condition.ActivityConditions.NEW_ACTIVITY_COND_NOT_FINISH_TALK;

import emu.grasscutter.game.activity.*;
import emu.grasscutter.game.activity.condition.*;

@ActivityCondition(NEW_ACTIVITY_COND_NOT_FINISH_TALK)
public class NotFinishTalk extends ActivityConditionBaseHandler {
    @Override
    public boolean execute(
            PlayerActivityData activityData, ActivityConfigItem activityConfig, int... params) {
        return activityData.getPlayer().getQuestManager().getMainQuests().int2ObjectEntrySet().stream()
                .noneMatch(
                        q ->
                                q.getValue().getTalks().get(params[0])
                                        != null); // FIXME taken from ContentCompleteTalk
    }
}
