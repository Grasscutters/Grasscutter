package emu.grasscutter.game.quest.conditions;

import static emu.grasscutter.game.quest.enums.QuestCond.QUEST_COND_QUEST_GLOBAL_VAR_EQUAL;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValueCond;
import lombok.val;

@QuestValueCond(QUEST_COND_QUEST_GLOBAL_VAR_EQUAL)
public class ConditionQuestGlobalVarEqual extends BaseCondition {

    @Override
    public boolean execute(
            Player owner,
            QuestData questData,
            QuestData.QuestAcceptCondition condition,
            String paramStr,
            int... params) {
        val questId = condition.getParam()[0];
        val targetValue = condition.getParam()[1];
        Integer questGlobalVarValue = owner.getQuestManager().getQuestGlobalVarValue(questId);
        Grasscutter.getLogger()
                .debug("questGlobarVar {} {} : {}", questId, targetValue, questGlobalVarValue);
        return questGlobalVarValue == targetValue;
    }
}
