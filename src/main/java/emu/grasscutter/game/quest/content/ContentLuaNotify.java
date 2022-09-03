package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTrigger.QUEST_CONTENT_LUA_NOTIFY)
public class ContentLuaNotify extends QuestBaseHandler {

	@Override
	public boolean execute(GameQuest quest, QuestCondition condition, String paramStr, int... params) {
		return condition.getParamStr().equals(paramStr);
	}

}
