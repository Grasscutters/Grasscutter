package emu.grasscutter.game.quest.conditions;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;

@QuestValue(QuestTrigger.QUEST_COND_PLAYER_LEVEL_EQUAL_GREATER)
public class ConditionPlayerLevelEqualGreater extends QuestBaseHandler {

	@Override
	public boolean execute(Player player, QuestCondition condition, String paramStr, int... params) {
		return player.getLevel() >= params[0];
	}

}
