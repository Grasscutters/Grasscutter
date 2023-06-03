package emu.grasscutter.game.quest.content;

import static emu.grasscutter.game.quest.enums.QuestContent.QUEST_CONTENT_LUA_NOTIFY;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueContent;

@QuestValueContent(QUEST_CONTENT_LUA_NOTIFY)
public class ContentLuaNotify extends BaseContent {

    @Override
    public boolean execute(
            GameQuest quest, QuestData.QuestContentCondition condition, String paramStr, int... params) {
        return condition.getParamStr().equals(paramStr)
                && condition.getCount()
                        <= quest.getOwner().getPlayerProgress().getCurrentProgress(paramStr);
    }
}
