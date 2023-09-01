package emu.grasscutter.game.quest.exec;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValueExec(QuestExec.QUEST_EXEC_DEACTIVE_ITEM_GIVING)
public final class ExecDeactivateItemGiving extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        var questManager = quest.getOwner().getQuestManager();

        var givingId = Integer.parseInt(condition.getParam()[0]);
        try {
            questManager.removeGivingItemAction(givingId);
            return true;
        } catch (IllegalStateException ignored) {
            Grasscutter.getLogger()
                    .warn(
                            "Quest {} attempted to remove give action {} twice.",
                            quest.getSubQuestId(),
                            givingId);
            return false;
        }
    }
}
