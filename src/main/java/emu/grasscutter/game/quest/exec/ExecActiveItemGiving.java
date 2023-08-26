package emu.grasscutter.game.quest.exec;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData.QuestExecParam;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValueExec(QuestExec.QUEST_EXEC_ACTIVE_ITEM_GIVING)
public final class ExecActiveItemGiving extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestExecParam condition, String... paramStr) {
        var player = quest.getOwner();
        var questManager = player.getQuestManager();

        var givingId = Integer.parseInt(condition.getParam()[0]);
        try {
            questManager.addGiveItemAction(givingId);

            Grasscutter.getLogger()
                    .debug("Quest {} added give action {}.", quest.getSubQuestId(), givingId);
            return true;
        } catch (IllegalStateException ignored) {
            Grasscutter.getLogger()
                    .warn("Quest {} attempted to add give action {} twice.", quest.getSubQuestId(), givingId);
            return false;
        }
    }
}
