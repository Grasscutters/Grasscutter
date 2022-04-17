package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarGainCostumeNotifyOuterClass.AvatarGainCostumeNotify;

public class PacketAvatarGainCostumeNotify extends GenshinPacket {
	
	public PacketAvatarGainCostumeNotify(int costumeId) {
		super(PacketOpcodes.AvatarGainCostumeNotify);

		AvatarGainCostumeNotify proto = AvatarGainCostumeNotify.newBuilder()
				.setCostumeId(costumeId)
				.build();
		
		this.setData(proto);
	}
}
