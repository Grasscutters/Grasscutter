package emu.grasscutter.game.talk.exec;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.TalkConfigData;
import emu.grasscutter.data.excels.TalkConfigData.TalkExecParam;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.talk.*;
import emu.grasscutter.game.world.Position;

@TalkValueExec(TalkExec.TALK_EXEC_TRANS_SCENE_DUMMY_POINT)
public final class ExecTransSceneDummyPoint extends TalkExecHandler {
    @Override
    public void execute(Player player, TalkConfigData talkData, TalkExecParam execParam) {
        // param[0] == sceneid, param[1] == position
        if (execParam.getParam().length < 2) return;

        var fullGlobals =
                GameData.getScriptSceneDataMap().get("flat.luas.scenes.full_globals.lua.json");
        if (fullGlobals == null) return;

        var dummyPointScript =
                fullGlobals
                        .getScriptObjectList()
                        .get(
                                execParam.getParam()[0] + "/scene" + execParam.getParam()[0] + "_dummy_points.lua");
        if (dummyPointScript == null) return;

        var dummyPointMap = dummyPointScript.getDummyPoints();
        if (dummyPointMap == null) return;

        var transmitPosPos = dummyPointMap.get(execParam.getParam()[1] + ".pos");
        // List<Float> transmitPosRot = dummyPointMap.get(e.getParam()[1] + ".rot"); would be useful
        // when transportation consider rotation
        if (transmitPosPos == null || transmitPosPos.isEmpty()) return;

        player
                .getWorld()
                .transferPlayerToScene(
                        player,
                        Integer.parseInt(execParam.getParam()[0]),
                        new Position(transmitPosPos.get(0), transmitPosPos.get(1), transmitPosPos.get(2)));
    }
}
