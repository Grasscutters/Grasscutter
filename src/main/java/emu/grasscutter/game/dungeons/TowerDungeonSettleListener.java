package emu.grasscutter.game.dungeons;

import emu.grasscutter.game.world.Scene;
import emu.grasscutter.server.packet.send.PacketDungeonSettleNotify;
import emu.grasscutter.server.packet.send.PacketTowerFloorRecordChangeNotify;
import emu.grasscutter.utils.Utils;

public class TowerDungeonSettleListener implements DungeonSettleListener {

    @Override
    public void onDungeonSettle(Scene scene) {
        if (scene.getScriptManager().getVariables().containsKey("stage")
            && scene.getScriptManager().getVariables().get("stage") == 1) {
            return;
        }
        scene.setAutoCloseTime(Utils.getCurrentSeconds() + 1000);
        var towerManager = scene.getPlayers().get(0).getTowerManager();

        towerManager.notifyCurLevelRecordChangeWhenDone(3);
        scene.broadcastPacket(new PacketTowerFloorRecordChangeNotify(
            towerManager.getCurrentFloorId(),
            3,
            towerManager.canEnterScheduleFloor()
        ));

        scene.broadcastPacket(new PacketDungeonSettleNotify(
            scene.getChallenge(),
            towerManager.hasNextFloor(),
            towerManager.hasNextLevel(),
            towerManager.hasNextLevel() ? 0 : towerManager.getNextFloorId()
        ));

    }
}
