package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValueContent(QuestContent.QUEST_CONTENT_NONE)
public class BaseContent extends QuestBaseHandler<QuestData.QuestContentCondition> {

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        // TODO Auto-generated method stub
        return false;
    }
}
