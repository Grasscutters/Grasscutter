package emu.grasscutter.game.quest.content;

import emu.grasscutter.data.def.QuestData.QuestCondition;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTrigger.QUEST_CONTENT_FINISH_PLOT)
public class ContentFinishPlot extends QuestBaseHandler {

	@Override
	public boolean execute(GameQuest quest, QuestCondition condition, int... params) {
		return condition.getParam()[0] == params[0];
	}
	
}
