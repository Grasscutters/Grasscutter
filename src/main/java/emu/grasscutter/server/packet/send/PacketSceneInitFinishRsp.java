package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SceneInitFinishRspOuterClass.SceneInitFinishRsp;

public class PacketSceneInitFinishRsp extends BasePacket {

    public PacketSceneInitFinishRsp(Player player) {
        super(PacketOpcodes.SceneInitFinishRsp, 11);

        SceneInitFinishRsp p =
                SceneInitFinishRsp.newBuilder().setEnterSceneToken(player.getEnterSceneToken()).build();

        this.setData(p.toByteArray());
    }
}
