package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestContent;

@QuestValueContent(QuestContent.QUEST_CONTENT_FINISH_ITEM_GIVING)
public final class ContentFinishGivingItem extends BaseContent {
    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        if(condition.getParam().length == 1) return condition.getParam()[0] == params[0];
        return condition.getParam()[0] == params[0] && condition.getParam()[1] == params[1];
    }
}
