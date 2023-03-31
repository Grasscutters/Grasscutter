package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PersonalSceneJumpRspOuterClass.PersonalSceneJumpRsp;
import emu.grasscutter.utils.Position;

public class PacketPersonalSceneJumpRsp extends BasePacket {

    public PacketPersonalSceneJumpRsp(int sceneId, Position pos) {
        super(PacketOpcodes.PersonalSceneJumpRsp);

        PersonalSceneJumpRsp proto = PersonalSceneJumpRsp.newBuilder()
            .setDestSceneId(sceneId)
            .setDestPos(pos.toProto())
            .build();

        this.setData(proto);
    }
}
