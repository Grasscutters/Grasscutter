package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarWearFlycloakRspOuterClass.AvatarWearFlycloakRsp;

public class PacketAvatarWearFlycloakRsp extends GenshinPacket {
	public PacketAvatarWearFlycloakRsp(long avatarGuid, int costumeId) {
		super(PacketOpcodes.AvatarWearFlycloakRsp);

		AvatarWearFlycloakRsp proto = AvatarWearFlycloakRsp.newBuilder()
				.setAvatarGuid(avatarGuid)
				.setFlycloakId(costumeId)
				.build();
		
		this.setData(proto);
	}
	
	public PacketAvatarWearFlycloakRsp() {
		super(PacketOpcodes.AvatarWearFlycloakRsp);

		AvatarWearFlycloakRsp proto = AvatarWearFlycloakRsp.newBuilder()
				.setRetcode(1)
				.build();
		
		this.setData(proto);
	}
}
