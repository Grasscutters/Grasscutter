package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.def.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;
@QuestValue(QuestTrigger.QUEST_CONTENT_LUA_NOTIFY)
public class ContentLuaNotify extends QuestBaseHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, int... params) {
        return false;
    }

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, String... params) {
        return condition.getParamStr().equals(params[0]);
    }

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExec condition, String... params) {
        return false;
    }
}
