package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueContent;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_OBTAIN_ITEM;

@QuestValueContent(QUEST_CONTENT_OBTAIN_ITEM)
public class ContentObtainItem extends BaseContent {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        var targetCount = condition.getCount();
        if (targetCount == 0) {
            targetCount = 1;
        }
        return condition.getParam()[0] == params[0] && targetCount <= params[1];
    }
}
