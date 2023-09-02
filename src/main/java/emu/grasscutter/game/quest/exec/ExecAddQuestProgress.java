package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;
import java.util.Arrays;

@QuestValueExec(QuestExec.QUEST_EXEC_ADD_QUEST_PROGRESS)
public final class ExecAddQuestProgress extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        var param =
                Arrays.stream(paramStr).filter(i -> !i.isBlank()).mapToInt(Integer::parseInt).toArray();

        quest.getOwner().getProgressManager().addQuestProgress(param[0], param[1]);

        return true;
    }
}
