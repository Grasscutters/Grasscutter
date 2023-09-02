package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_LEAVE_SCENE;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;

@QuestValueContent(QUEST_CONTENT_LEAVE_SCENE)
public class ContentLeaveScene extends BaseContent {

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        return condition.getParam()[0] == params[0];
    }
}
