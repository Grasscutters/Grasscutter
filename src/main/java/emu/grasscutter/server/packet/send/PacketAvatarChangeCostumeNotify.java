package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarChangeCostumeNotifyOuterClass.AvatarChangeCostumeNotify;

public class PacketAvatarChangeCostumeNotify extends GenshinPacket {
	
	public PacketAvatarChangeCostumeNotify(EntityAvatar entity) {
		super(PacketOpcodes.AvatarChangeCostumeNotify);

		AvatarChangeCostumeNotify proto = AvatarChangeCostumeNotify.newBuilder()
				.setEntity(entity.toProto())
				.build();
		
		this.setData(proto);
	}
}
