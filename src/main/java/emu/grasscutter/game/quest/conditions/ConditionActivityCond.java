package emu.grasscutter.game.quest.conditions;

import static emu.grasscutter.game.quest.enums.QuestCond.QUEST_COND_ACTIVITY_COND;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValueCond;
import lombok.val;

@QuestValueCond(QUEST_COND_ACTIVITY_COND)
public class ConditionActivityCond extends BaseCondition {

    @Override
    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        val activityCondId = condition.getParam()[0];
        val targetState = condition.getParam()[1]; // only 1 for now
        return owner.getActivityManager().meetsCondition(activityCondId) == (targetState == 1);
    }
}
