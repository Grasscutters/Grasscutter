package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetFriendShowAvatarInfoRspOuterClass.GetFriendShowAvatarInfoRsp;
import emu.grasscutter.net.proto.ShowAvatarInfoOuterClass.ShowAvatarInfo;

import java.util.List;

@Opcodes(PacketOpcodes.GetFriendShowAvatarInfoRsp)
public class PacketGetFriendShowAvatarInfoRsp extends BasePacket {

    public PacketGetFriendShowAvatarInfoRsp(int uid, List<ShowAvatarInfo> showAvatarInfoList) {
        super(PacketOpcodes.GetFriendShowAvatarInfoRsp);

        GetFriendShowAvatarInfoRsp.Builder p = GetFriendShowAvatarInfoRsp.newBuilder()
            .setUid(uid)
            .addAllShowAvatarInfoList(showAvatarInfoList);

        this.setData(p.build());
    }

}
