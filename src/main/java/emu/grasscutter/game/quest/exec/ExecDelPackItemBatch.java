package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValueExec(QuestExec.QUEST_EXEC_DEL_PACK_ITEM_BATCH)
public class ExecDelPackItemBatch extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        // input is like this: "100497:999,100498:999,100499:999"
        var items = paramStr[0].split(",");
        boolean success = true;
        for (var itemString : items) {
            var itemFields = itemString.split(":");
            var itemId = Integer.parseInt(itemFields[0]);
            var amount = Integer.parseInt(itemFields[1]);
            if (!quest.getOwner().getInventory().removeItemById(itemId, amount)) {
                success = false;
            }
        }
        return success;
    }
}
