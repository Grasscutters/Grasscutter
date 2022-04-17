package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetPlayerSocialDetailRspOuterClass.GetPlayerSocialDetailRsp;
import emu.grasscutter.net.proto.SocialDetailOuterClass.SocialDetail;

public class PacketGetPlayerSocialDetailRsp extends GenshinPacket {
	
	public PacketGetPlayerSocialDetailRsp(SocialDetail.Builder detail) {
		super(PacketOpcodes.GetPlayerSocialDetailRsp);

		GetPlayerSocialDetailRsp.Builder proto = GetPlayerSocialDetailRsp.newBuilder();
		
		if (detail != null) {
			proto.setDetailData(detail);
		} else {
			proto.setRetcode(1);
		}

		this.setData(proto);
	}
}
