package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EnterSceneDoneRspOuterClass.EnterSceneDoneRsp;

public class PacketEnterSceneDoneRsp extends BasePacket {

    public PacketEnterSceneDoneRsp(Player player) {
        super(PacketOpcodes.EnterSceneDoneRsp);

        EnterSceneDoneRsp p =
                EnterSceneDoneRsp.newBuilder().setEnterSceneToken(player.getEnterSceneToken()).build();

        this.setData(p);
    }
}
