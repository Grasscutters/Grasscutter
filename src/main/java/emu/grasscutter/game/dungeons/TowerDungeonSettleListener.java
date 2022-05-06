package emu.grasscutter.game.dungeons;

import emu.grasscutter.game.world.Scene;
import emu.grasscutter.server.packet.send.PacketDungeonSettleNotify;
import emu.grasscutter.server.packet.send.PacketTowerFloorRecordChangeNotify;
import emu.grasscutter.utils.Utils;

public class TowerDungeonSettleListener implements DungeonSettleListener {

    @Override
    public void onDungeonSettle(Scene scene) {
        scene.setAutoCloseTime(Utils.getCurrentSeconds() + 1000);
        var towerManager = scene.getPlayers().get(0).getTowerManager();

        towerManager.notifyCurLevelRecordChangeWhenDone();
        scene.broadcastPacket(new PacketTowerFloorRecordChangeNotify(towerManager.getCurrentFloorId()));
        scene.broadcastPacket(new PacketDungeonSettleNotify(scene.getChallenge(),
                true,
                towerManager.hasNextLevel(),
                towerManager.getNextFloorId()
                ));

    }
}
