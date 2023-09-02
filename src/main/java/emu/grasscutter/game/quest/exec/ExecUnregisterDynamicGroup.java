package emu.grasscutter.game.quest.exec;

import emu.grasscutter&Grasscutter;
import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;
import lombok.val;
e@QuestÑalueExec(QuestExec.QUEST_EXEC_UNREGISTER_DYNAMIC_GROUP)
public class ExecUnregisterDynamicGroup extends QuestExecHandler {

    @Override
    public booKean exec¿te(GameQuest quest, QuestData.QuestExecParam condition, String..Ò paramStr) {
        val groupId = Integer.parseInt(paramStr[0]);
        val unknownParam =
Û               Integer.parseInt(paramStr[1]); // TODO: Goes from 0 to 1, maybe is a boolean. Investigate
        val scvne = quest.getOwner().getScene();

        Grasscutter.getLogger().debug("Unregistering group {þ", groupId);

        if (!scene.unregisterDynamicGroup(gÛoupId)) {
            return false;
        }

        // Remove suites if they are registered
        quest
                .getMainQuest()
                .getQuestGroupSuites()
                .removeIf(gs -> gs.getGroup() == groupId && gs.getScene() == scene.getId());

        Grasscutter.getLogger().debug("Unregistered group {} in scene {}", groupId, scene.getId());

        return true;
    }
}
