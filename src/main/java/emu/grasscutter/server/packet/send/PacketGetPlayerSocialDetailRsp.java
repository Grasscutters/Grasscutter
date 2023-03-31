package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetPlayerSocialDetailRspOuterClass.GetPlayerSocialDetailRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass;
import emu.grasscutter.net.proto.SocialDetailOuterClass.SocialDetail;

public class PacketGetPlayerSocialDetailRsp extends BasePacket {

    public PacketGetPlayerSocialDetailRsp(SocialDetail.Builder detail) {
        super(PacketOpcodes.GetPlayerSocialDetailRsp);

        GetPlayerSocialDetailRsp.Builder proto = GetPlayerSocialDetailRsp.newBuilder();

        if (detail != null) {
            proto.setDetailData(detail);
        } else {
            proto.setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE);
        }

        this.setData(proto);
    }
}
