package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_ENTER_MY_WORLD;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueContent;

@QuestValueContent(QUEST_CONTENT_ENTER_MY_WORLD)
public class ContentEnterMyWorld extends BaseContent {
    // params[0] scene ID
    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        var sceneId = condition.getParam()[0];
        if (sceneId != params[0]) return false;
        return quest.getOwner().getSceneId() == params[0];
    }
}
