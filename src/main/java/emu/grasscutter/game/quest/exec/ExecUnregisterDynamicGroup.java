package emu.grasscutter.game.quest.exec;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.excels.QuestData;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestValueExec;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.data.SceneBlock;
import emu.grasscutter.scripts.data.SceneGroup;
import lombok.val;

@QuestValueExec(QuestExec.QUEST_EXEC_UNREGISTER_DYNAMIC_GROUP)
public class ExecUnregisterDynamicGroup extends QuestExecHandler {

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        val groupId = Integer.parseInt(paramStr[0]);
        val unknownParam = Integer.parseInt(paramStr[1]); //TODO: Goes from 0 to 1, maybe is a boolean. Investigate
        val scene = quest.getOwner().getScene();

        Grasscutter.getLogger().warn("Unregistering group {}", groupId);

        if(!scene.unregisterDynamicGroup(groupId)){
            return false;
        }

        //Remove suites if they are registered
        quest.getMainQuest().getQuestGroupSuites().removeIf(gs -> gs.getGroup() == groupId && gs.getScene() == scene.getId());

        Grasscutter.getLogger().warn("Unregistered group {} in scene {}", groupId, scene.getId());

        return true;
    }

}
