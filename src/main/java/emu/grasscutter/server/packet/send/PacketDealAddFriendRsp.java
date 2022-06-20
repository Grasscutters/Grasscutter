package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DealAddFriendResultTypeOuterClass.DealAddFriendResultType;
import emu.grasscutter.net.proto.DealAddFriendRspOuterClass.DealAddFriendRsp;

public class PacketDealAddFriendRsp extends BasePacket {

    public PacketDealAddFriendRsp(int targetUid, DealAddFriendResultType result) {
        super(PacketOpcodes.DealAddFriendRsp);

        DealAddFriendRsp proto = DealAddFriendRsp.newBuilder()
            .setTargetUid(targetUid)
            .setDealAddFriendResult(result)
            .build();

        this.setData(proto);
    }
}
