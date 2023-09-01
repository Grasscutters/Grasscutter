package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.QuestExec;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;
import lombok.val;

@QuestValueExec(QuestExec.QUEST_EXEC_REFRESH_GROUP_SUITE)
public class ExecRefreshGroupSuite extends QuestExecHandler {

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        val sceneId = Integer.parseInt(paramStr[0]);
        val entries = paramStr[1].split(";");
        val scriptManager = quest.getOwner().getWorld().getSceneById(sceneId).getScriptManager();

        boolean result = true;
        for (var entry : entries) {
            val entryArray = entry.split(",");
            val groupId = Integer.parseInt(entryArray[0]);
            val suiteId = Integer.parseInt(entryArray[1]);

            if (!scriptManager.refreshGroupSuite(groupId, suiteId, quest)) {
                result = false;
            }
            scriptManager.getGroupById(groupId).dontUnload = true;
        }

        return result;
    }
}
