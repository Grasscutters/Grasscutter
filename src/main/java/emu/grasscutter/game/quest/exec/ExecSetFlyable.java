package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.props.PlayerProperty;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValueExec(QuestExec.QUEST_EXEC_SET_IS_FLYABLE)
public final class ExecSetFlyable extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        var canFly = Integer.parseInt(paramStr[0]);
        quest.getOwner().setProperty(PlayerProperty.PROP_IS_FLYABLE, canFly);
        return true;
    }
}
