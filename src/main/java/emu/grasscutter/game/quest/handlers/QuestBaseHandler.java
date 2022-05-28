package emu.grasscutter.game.quest.handlers;

import emu.grasscutter.data.def.QuestData;
import emu.grasscutter.data.def.QuestData.QuestCondition;
import emu.grasscutter.game.quest.GameQuest;

public abstract class QuestBaseHandler {

	public abstract boolean execute(GameQuest quest, QuestCondition condition, int... params);
	public abstract boolean execute(GameQuest quest, QuestCondition condition, String... params);
	public abstract boolean execute(GameQuest quest, QuestData.QuestExec condition, String... params);
}
