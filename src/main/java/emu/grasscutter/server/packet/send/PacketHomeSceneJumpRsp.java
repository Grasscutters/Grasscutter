package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeSceneJumpRspOuterClass;

public class PacketHomeSceneJumpRsp extends BasePacket {

    public PacketHomeSceneJumpRsp(boolean enterRoomScene) {
        super(PacketOpcodes.HomeSceneJumpRsp);

        var proto = HomeSceneJumpRspOuterClass.HomeSceneJumpRsp.newBuilder();

        proto.setIsEnterRoomScene(enterRoomScene);

        this.setData(proto);
    }
}
