package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTrigger.QUEST_COND_LUA_NOTIFY)
public class ConditionLuaNotify extends QuestBaseHandler {

	@Override
	public boolean execute(GameQuest quest, QuestCondition condition, String paramStr, int... params) {
        return condition.getParam()[0] == Integer.parseInt(paramStr);
	}

}
