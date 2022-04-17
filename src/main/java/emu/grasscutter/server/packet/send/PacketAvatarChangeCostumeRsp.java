package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarChangeCostumeRspOuterClass.AvatarChangeCostumeRsp;

public class PacketAvatarChangeCostumeRsp extends GenshinPacket {
	
	public PacketAvatarChangeCostumeRsp(long avatarGuid, int costumeId) {
		super(PacketOpcodes.AvatarChangeCostumeRsp);

		AvatarChangeCostumeRsp proto = AvatarChangeCostumeRsp.newBuilder()
				.setAvatarGuid(avatarGuid)
				.setCostumeId(costumeId)
				.build();
		
		this.setData(proto);
	}
	
	public PacketAvatarChangeCostumeRsp() {
		super(PacketOpcodes.AvatarChangeCostumeRsp);

		AvatarChangeCostumeRsp proto = AvatarChangeCostumeRsp.newBuilder()
				.setRetcode(1)
				.build();
		
		this.setData(proto);
	}
}
