package emu.grasscutter.game.quest.handlers;

import emu.grasscutter.data.excels.QuestData.QuestCondition;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.game.quest.GameQuest;
import jdk.jshell.spi.ExecutionControl;

public abstract class QuestBaseHandler {

	public  boolean execute(GameQuest quest, QuestCondition condition, String paramStr, int... params) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("method not overridden");
    }

    public boolean execute(Player player, QuestCondition condition, String paramStr, int... params)throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("method not overridden");
    }

    public boolean execute(GameMainQuest mainQuest, QuestCondition condition, String paramStr, int... params)throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("method not overridden");
    }

}
