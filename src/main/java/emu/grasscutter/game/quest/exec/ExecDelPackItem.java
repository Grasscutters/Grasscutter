package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;

@QuestValueExec(QuestExec.QUEST_EXEC_DEL_PACK_ITEM)
public class ExecDelPackItem extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        int itemId = Integer.parseInt(paramStr[0]);
        int amount = Integer.parseInt(paramStr[1]);
        return quest.getOwner().getInventory().removeItemById(itemId, amount);
    }
}
