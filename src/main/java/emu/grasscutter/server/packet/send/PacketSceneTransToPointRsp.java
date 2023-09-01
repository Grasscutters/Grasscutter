package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.net.proto.SceneTransToPointRspOuterClass.SceneTransToPointRsp;

public class PacketSceneTransToPointRsp extends BasePacket {

    public PacketSceneTransToPointRsp(Player player, int pointId, int sceneId) {
        super(PacketOpcodes.SceneTransToPointRsp);

        SceneTransToPointRsp proto =
                SceneTransToPointRsp.newBuilder()
                        .setRetcode(0)
                        .setPointId(pointId)
                        .setSceneId(sceneId)
                        .build();

        this.setData(proto);
    }

    public PacketSceneTransToPointRsp() {
        super(PacketOpcodes.SceneTransToPointRsp);

        SceneTransToPointRsp proto =
                SceneTransToPointRsp.newBuilder()
                        .setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE) // Internal server error
                        .build();

        this.setData(proto);
    }
}
