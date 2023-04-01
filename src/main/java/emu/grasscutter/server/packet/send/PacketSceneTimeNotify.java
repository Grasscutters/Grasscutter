package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SceneTimeNotifyOuterClass.SceneTimeNotify;

public class PacketSceneTimeNotify extends BasePacket {

    public PacketSceneTimeNotify(Player player) {
        super(PacketOpcodes.SceneTimeNotify);

        var proto =
                SceneTimeNotify.newBuilder()
                        .setIsPaused(player.isPaused())
                        .setSceneId(player.getSceneId())
                        .setSceneTime(player.getScene().getSceneTime())
                        .build();

        this.setData(proto);
    }

    public PacketSceneTimeNotify(Scene scene) {
        super(PacketOpcodes.SceneTimeNotify);

        var proto =
                SceneTimeNotify.newBuilder()
                        .setSceneId(scene.getId())
                        .setSceneTime(scene.getSceneTime())
                        .setIsPaused(scene.isPaused())
                        .build();

        this.setData(proto);
    }
}
