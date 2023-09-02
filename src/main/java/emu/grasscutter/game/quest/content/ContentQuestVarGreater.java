package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_QUEST_VAR_GREATER;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;

@QuestValueContent(QUEST_CONTENT_QUEST_VAR_GREATER)
public class ContentQuestVarGreater extends BaseContent {

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        int questVarValue = quest.getMainQuest().getQuestVars()[condition.getParam()[0]];
        Grasscutter.getLogger().debug("questVar {} : {}", condition.getParam()[0], questVarValue);
        return questVarValue > condition.getParam()[1];
    }
}
