package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_LUA_NOTIFY;

@QuestValueContent(QUEST_CONTENT_LUA_NOTIFY)
public class ContentLuaNotify extends BaseContent {

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        var targetAmount = condition.getCount();
        if (targetAmount == 0) {
            targetAmount = 1;
        }
        return targetAmount
                <= quest.getOwner().getPlayerProgress().getCurrentProgress(condition.getParamStr());
    }
}
