package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;
import emu.grasscutter.server.packet.send.PacketScenePlayerLocationNotify;

@QuestValueExec(QuestExec.QUEST_EXEC_ROLLBACK_PARENT_QUEST)
public class ExecRollbackParentQuest extends QuestExecHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        var targetPosition = quest.getMainQuest().rewind();
        if (targetPosition == null) {
            return false;
        }
        quest.getOwner().getPosition().set(targetPosition.get(0));
        quest.getOwner().getRotation().set(targetPosition.get(1));
        quest.getOwner().sendPacket(new PacketScenePlayerLocationNotify(quest.getOwner().getScene()));
        // todo proper reset and warp
        return true;
    }
}
