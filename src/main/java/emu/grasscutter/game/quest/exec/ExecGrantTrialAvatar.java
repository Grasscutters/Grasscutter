package emu.grasscutter.game.quest.exec;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValueExec(QuestExec.QUEST_EXEC_GRANT_TRIAL_AVATAR)
public class ExecGrantTrialAvatar extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        try {
            quest
                    .getOwner()
                    .getTeamManager()
                    .addTrialAvatar(Integer.parseInt(paramStr[0]), quest.getMainQuestId());
            Grasscutter.getLogger()
                    .debug("Added trial avatar to team for quest {}", quest.getSubQuestId());
            return true;
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
