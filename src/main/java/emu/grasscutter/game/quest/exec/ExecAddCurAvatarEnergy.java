package emu.grasscutter.game.quest.exec;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValueExec(QuestExec.QUEST_EXEC_ADD_CUR_AVATAR_ENERGY)
public class ExecAddCurAvatarEnergy extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        Grasscutter.getLogger().debug("Energy refilled");
        return quest.getOwner().getEnergyManager().refillActiveEnergy();
    }
}
