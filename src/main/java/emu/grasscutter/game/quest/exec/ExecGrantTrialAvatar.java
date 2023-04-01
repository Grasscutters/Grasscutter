package emu.grasscutter.game.quest.exec;

import emu.grasscutter.Grasscutter;

import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueExec;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValueExec(QuestExec.QUEST_EXEC_GRANT_TRIAL_AVATAR)
public class ExecGrantTrialAvatar extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        if (quest.getOwner().addTrialAvatarForQuest(Integer.parseInt(paramStr[0]), quest.getMainQuestId())) {
            Grasscutter.getLogger().info("Added trial avatar to team for quest {}", quest.getSubQuestId());
            return true;
        }
        return false;
    }
}
