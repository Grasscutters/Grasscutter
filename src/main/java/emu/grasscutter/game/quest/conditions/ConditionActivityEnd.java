package emu.grasscutter.game.quest.conditions;

import static emu.grasscutter.game.quest.enums.QuestCond.QUEST_COND_ACTIVITY_END;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValueCond;
import lombok.val;

@QuestValueCond(QUEST_COND_ACTIVITY_END)
public class ConditionActivityEnd extends BaseCondition {

    @Override
    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        val activityId = condition.getParam()[0];
        return owner.getActivityManager().hasActivityEnded(activityId);
    }
}
