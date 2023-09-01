package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.excels.quest.QuestData;
import emu.grasscutter.game.quest.*;
import emu.grasscutter.game.quest.enums.*;
import emu.grasscutter.game.quest.handlers.QuestExecHandler;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.ScriptArgs;
import lombok.val;

@QuestValueExec(QuestExec.QUEST_EXEC_NOTIFY_GROUP_LUA)
public class ExecNotifyGroupLua extends QuestExecHandler {

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExecParam condition, String... paramStr) {
        val sceneId = Integer.parseInt(paramStr[0]);
        val groupId = Integer.parseInt(paramStr[1]);

        val scene = quest.getOwner().getScene();
        val scriptManager = scene.getScriptManager();

        if (scene.getId() != sceneId) {
            return false;
        }
        scene.runWhenFinished(
                () -> {
                    val eventType =
                            quest.getState() == QuestState.QUEST_STATE_FINISHED
                                    ? EventType.EVENT_QUEST_FINISH
                                    : EventType.EVENT_QUEST_START;
                    scriptManager.callEvent(
                            new ScriptArgs(
                                            groupId,
                                            eventType,
                                            quest.getSubQuestId(),
                                            quest.getState() == QuestState.QUEST_STATE_FINISHED ? 1 : 0)
                                    .setEventSource(quest.getSubQuestId()));
                });

        return true;
    }
}
