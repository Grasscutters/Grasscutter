package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_QUEST_STATE_NOT_EQUAL;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;

@QuestValueContent(QUEST_CONTENT_QUEST_STATE_NOT_EQUAL)
public class ContentQuestStateNotEqual extends BaseContent {

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        GameQuest checkQuest = quest.getOwner().getQuestManager().getQuestById(condition.getParam()[0]);

        if (checkQuest == null) {
            return false;
        }

        return checkQuest.getState().getValue() != condition.getParam()[1];
    }
}
