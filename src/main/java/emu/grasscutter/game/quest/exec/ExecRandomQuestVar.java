package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValueExec(QuestExec.QUEST_EXEC_RANDOM_QUEST_VAR)
public class ExecRandomQuestVar extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        quest
                .getMainQuest()
                .randomQuestVar(
                        Integer.parseInt(paramStr[0]),
                        Integer.parseInt(paramStr[1]),
                        Integer.parseInt(paramStr[2]));
        return true;
    }
}
