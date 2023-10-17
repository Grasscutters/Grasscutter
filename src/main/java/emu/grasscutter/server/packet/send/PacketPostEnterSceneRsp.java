package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PostEnterSceneRspOuterClass.PostEnterSceneRsp;

public class PacketPostEnterSceneRsp extends BasePacket {

    public PacketPostEnterSceneRsp(Player player) {
        super(PacketOpcodes.PostEnterSceneRsp);

        PostEnterSceneRsp p =
                PostEnterSceneRsp.newBuilder().setEnterSceneToken(player.getEnterSceneToken()).build();

        //
        // On moving to new scene:
        // Unfreeze dungeon entry points that have already been unlocked in this scene.
        player.unfreezeUnlockedScenePoints();

        this.setData(p);
    }
}
