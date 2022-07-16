package emu.grasscutter.game.quest.handlers;

import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.game.quest.GameQuest;
import jdk.jshell.spi.ExecutionControl;

public abstract class QuestBaseHandler {
	public abstract boolean execute(GameQuest quest, QuestCondition condition, String paramStr, int... params);
}
