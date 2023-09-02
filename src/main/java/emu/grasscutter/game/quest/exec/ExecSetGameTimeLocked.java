package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.excels.quest.QuestData.QuestExecParam;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;
import java.util.Objects;

@QuestValueExec(QuestExec.QUEST_EXEC_SET_IS_GAME_TIME_LOCKED)
public final class ExecSetGameTimeLocked extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestExecParam condition, String... paramStr) {
        var isLocked = Objects.equals(condition.getParam()[0], "1");
        quest.getOwner().getWorld().lockTime(isLocked);

        return true;
    }
}
