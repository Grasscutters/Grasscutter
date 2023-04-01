package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueExec;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;
import emu.grasscutter.Grasscutter;

@QuestValueExec(QuestExec.QUEST_EXEC_ADD_CUR_AVATAR_ENERGY)
public class ExecAddCurAvatarEnergy extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        Grasscutter.getLogger().info("Energy refilled");
        return quest.getOwner().getEnergyManager().refillEntityAvatarEnergy();
    }
}
