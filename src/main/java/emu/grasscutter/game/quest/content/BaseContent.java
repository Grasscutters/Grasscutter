package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.custom.QuestConfigData.QuestCondition;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.enums.QuestTriggerType;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTriggerType.QUEST_CONTENT_NONE)
public class BaseContent extends QuestBaseHandler {

	@Override
	public boolean execute(GameQuest quest, QuestCondition condition, int... params) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
