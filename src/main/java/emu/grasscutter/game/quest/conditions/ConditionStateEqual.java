package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.data.custom.QuestConfigData.QuestCondition;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.enums.QuestTriggerType;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTriggerType.QUEST_COND_STATE_EQUAL)
public class ConditionStateEqual extends QuestBaseHandler {

	@Override
	public boolean execute(GameQuest quest, QuestCondition condition, int... params) {
		GameQuest checkQuest = quest.getOwner().getQuestManager().getQuestById(condition.getParam()[0]);
		
		if (checkQuest != null) {
			return checkQuest.getState().getValue() == condition.getParam()[1];
		}
		
		return false; 
	}
	
}
