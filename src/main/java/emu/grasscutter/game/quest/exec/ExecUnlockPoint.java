package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValueExec(QuestExec.QUEST_EXEC_UNLOCK_POINT)
public class ExecUnlockPoint extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        // Unlock the trans point for the player.
        int sceneId = Integer.parseInt(paramStr[0]);
        int pointId = Integer.parseInt(paramStr[1]);

        // TODO: Determine if the point is a statue.
        // This is currently a hardcoded value.
        boolean isStatue = quest.getMainQuestId() == 303 || quest.getMainQuestId() == 352;

        // Done.
        return quest.getOwner().getProgressManager().unlockTransPoint(sceneId, pointId, isStatue);
    }
}
