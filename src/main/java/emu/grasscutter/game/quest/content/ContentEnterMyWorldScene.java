package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueContent;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_ENTER_MY_WORLD_SCENE;

@QuestValueContent(QUEST_CONTENT_ENTER_MY_WORLD_SCENE)
public class ContentEnterMyWorldScene extends BaseContent {
    // params[0] scene ID
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        return condition.getParam()[0] == params[0];
    }

}
