package emu.grasscutter.game.quest.exec;

import emu.grasscutter.data.def.QuestData;
import emu.grasscutter.database.DatabaseHelper;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.handlers.QuestBaseHandler;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.scripts.SceneScriptManager;
import emu.grasscutter.scripts.data.SuiteIndex;

public class ExecRefreshGroupSuite extends QuestBaseHandler {
    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, int... params) {
        return false;
    }

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestCondition condition, String... params) {
        return false;
    }

    @Override
    public boolean execute(GameQuest quest, QuestData.QuestExec condition, String... params) {
        Player player = quest.getOwner();
        SceneScriptManager scriptManager = player.getScene().getScriptManager();
        int sceneId = Integer.parseInt(params[0]);
        int groupId = Integer.parseInt(params[1].split(",")[0]);
        int suiteIndex=Integer.parseInt(params[1].split(",")[1]);
        //if player in this scene,immediately refresh
        if (player.getScene().getId()==sceneId) {
            scriptManager.refreshGroup(scriptManager.getGroupById(groupId),suiteIndex);
        }
        SuiteIndex suite = new SuiteIndex(sceneId, groupId, suiteIndex, player.getUid());
        DatabaseHelper.saveSuiteIndex(suite);
        return false;
    }
}
