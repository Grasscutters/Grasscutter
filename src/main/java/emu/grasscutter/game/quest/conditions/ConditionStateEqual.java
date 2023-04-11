package emu.grasscutter.game.quest.conditions;

import static emu.grasscutter.game.quest.enums.QuestCond.QUEST_COND_STATE_EQUAL;

import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueCond;
import lombok.val;

@QuestValueCond(QUEST_COND_STATE_EQUAL)
public class ConditionStateEqual extends BaseCondition {

    @Override
    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        val questId = condition.getParam()[0];
        val questStateValue = condition.getParam()[1];
        GameQuest checkQuest = owner.getQuestManager().getQuestById(questId);
        if (checkQuest == null) {
            /*
            Will spam the console
            */
            // Grasscutter.getLogger().debug("Warning: quest {} hasn't been started yet!",
            // condition.getParam()[0]);
            return false;
        }
        return checkQuest.getState().getValue() == questStateValue;
    }
}
