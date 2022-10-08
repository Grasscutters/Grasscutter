package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetFriendShowNameCardInfoRspOuterClass;

import java.util.List;

public class PacketGetFriendShowNameCardInfoRsp extends BasePacket {
    public PacketGetFriendShowNameCardInfoRsp(int uid, List<Integer> cardIds) {
        super(PacketOpcodes.GetFriendShowNameCardInfoRsp);

        GetFriendShowNameCardInfoRspOuterClass.GetFriendShowNameCardInfoRsp rsp = GetFriendShowNameCardInfoRspOuterClass.GetFriendShowNameCardInfoRsp.newBuilder()
            .setUid(uid)
            .addAllShowNameCardIdList(cardIds)
            .build();

        this.setData(rsp);
    }
}
