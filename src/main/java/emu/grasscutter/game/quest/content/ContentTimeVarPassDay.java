package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestContent;
import lombok.val;

@QuestValueContent(QuestContent.QUEST_CONTENT_TIME_VAR_PASS_DAY)
public class ContentTimeVarPassDay extends BaseContent {
    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        val mainQuestId = condition.getParam()[0];
        val timeVarIndex = condition.getParam()[1];
        val minDays = Integer.parseInt(condition.getParamStr());

        val mainQuest = quest.getOwner().getQuestManager().getMainQuestById(mainQuestId);

        if (mainQuest == null) {
            return false;
        }

        val daysSinceTimeVar = mainQuest.getDaysSinceTimeVar(timeVarIndex);
        if (daysSinceTimeVar == -1) {
            return false;
        }

        return daysSinceTimeVar >= minDays;
    }
}
