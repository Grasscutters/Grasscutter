package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTrigger.QUEST_COND_STATE_EQUAL)
public class ConditionStateEqual extends QuestBaseHandler {

	@Override
	public boolean execute(GameQuest quest, QuestCondition condition, String paramStr, int... params) {
		GameQuest checkQuest = quest.getOwner().getQuestManager().getQuestById(params[0]);

		if (checkQuest != null) {
			return checkQuest.getState().getValue() == params[1];
		}

		return false;
	}

}
