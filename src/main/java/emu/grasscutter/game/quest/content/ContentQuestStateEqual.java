package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_QUEST_STATE_EQUAL;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueContent;

@QuestValueContent(QUEST_CONTENT_QUEST_STATE_EQUAL)
public class ContentQuestStateEqual extends BaseContent {

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        var questId = condition.getParam()[0];
        if(questId != params[0]) return false;
        GameQuest checkQuest = quest.getOwner().getQuestManager().getQuestById(questId);
        if (checkQuest == null) {
            return false;
        }
        return checkQuest.getState().getValue() == params[1];
    }
}
