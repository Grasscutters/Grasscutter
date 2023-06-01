package emu.grasscutter.game.quest.handlers;

import emu.grasscutter.data.excels.quest.QuestData.QuestCondition;
import emu.grasscutter.game.quest.GameQuest;

public abstract class QuestBaseHandler<T extends QuestCondition<?>> {

    public abstract boolean execute(GameQuest quest, T condition, String paramStr, int... params);
}
