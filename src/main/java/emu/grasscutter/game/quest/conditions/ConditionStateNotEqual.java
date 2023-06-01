package emu.grasscutter.game.quest.conditions;

import static emu.grasscutter.game.quest.enums.QuestCond.QUEST_COND_STATE_NOT_EQUAL;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValueCond;

@QuestValueCond(QUEST_COND_STATE_NOT_EQUAL)
public class ConditionStateNotEqual extends BaseCondition {

    @Override
    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        var questId = condition.getParam()[0];
        var questStateValue = condition.getParam()[1];
        var checkQuest = owner.getQuestManager().getQuestById(questId);

        return checkQuest != null && checkQuest.getState().getValue() != questStateValue;
    }
}
