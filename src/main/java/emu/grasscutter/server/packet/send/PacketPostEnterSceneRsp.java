package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PostEnterSceneRspOuterClass.PostEnterSceneRsp;

public class PacketPostEnterSceneRsp extends BasePacket {

    public PacketPostEnterSceneRsp(Player player) {
        super(PacketOpcodes.PostEnterSceneRsp);

        PostEnterSceneRsp p = PostEnterSceneRsp.newBuilder()
            .setEnterSceneToken(player.getEnterSceneToken())
            .build();

        this.setData(p);
    }
}
