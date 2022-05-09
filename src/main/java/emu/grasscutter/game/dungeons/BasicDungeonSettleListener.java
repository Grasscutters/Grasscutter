package emu.grasscutter.game.dungeons;

import emu.grasscutter.game.world.Scene;
import emu.grasscutter.server.packet.send.PacketDungeonSettleNotify;
import emu.grasscutter.utils.Utils;

public class BasicDungeonSettleListener implements DungeonSettleListener {

    @Override
    public void onDungeonSettle(Scene scene) {
        scene.setAutoCloseTime(Utils.getCurrentSeconds() + 1000);
        scene.broadcastPacket(new PacketDungeonSettleNotify(scene.getChallenge()));
    }
}
