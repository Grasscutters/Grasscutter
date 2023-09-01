package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DeleteFriendRspOuterClass.DeleteFriendRsp;

public class PacketDeleteFriendRsp extends BasePacket {

    public PacketDeleteFriendRsp(int targetUid) {
        super(PacketOpcodes.DeleteFriendRsp);

        DeleteFriendRsp proto = DeleteFriendRsp.newBuilder().setTargetUid(targetUid).build();

        this.setData(proto);
    }
}
