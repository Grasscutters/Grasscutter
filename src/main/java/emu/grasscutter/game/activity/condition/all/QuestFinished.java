package emu.grasscutter.game.activity.condition.all;

import static emu.grasscutter.game.activity.condition.ActivityConditions.NEW_ACTIVITY_COND_QUEST_FINISH;

import emu.grasscutter.game.activity.*;
import emu.grasscutter.game.activity.condition.*;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.enums.QuestState;

@ActivityCondition(NEW_ACTIVITY_COND_QUEST_FINISH)
public class QuestFinished extends ActivityConditionBaseHandler {
    @Override
    public boolean execute(
            PlayerActivityData activityData, ActivityConfigItem activityConfig, int... params) {
        GameQuest quest = activityData.getPlayer().getQuestManager().getQuestById(params[0]);

        return quest != null && quest.getState() == QuestState.QUEST_STATE_FINISHED;
    }
}
