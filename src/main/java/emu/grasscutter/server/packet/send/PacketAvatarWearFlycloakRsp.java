package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarWearFlycloakRspOuterClass.AvatarWearFlycloakRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketAvatarWearFlycloakRsp extends BasePacket {
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
				.setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE)
				.build();
		
		this.setData(proto);
	}
}
