package emu.grasscutter.game.quest.exec;


import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValue;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValue(QuestTrigger.QUEST_EXEC_DEC_QUEST_GLOBAL_VAR)
public class ExecDecQuestGlobalVar extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        quest.getOwner().getQuestManager().decQuestGlobalVarValue(Integer.valueOf(paramStr[0]),Integer.valueOf(paramStr[1]));
        return true;
    }
}
