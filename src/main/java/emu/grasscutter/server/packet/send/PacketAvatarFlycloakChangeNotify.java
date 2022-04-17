package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarFlycloakChangeNotifyOuterClass.AvatarFlycloakChangeNotify;

public class PacketAvatarFlycloakChangeNotify extends GenshinPacket {
	
	public PacketAvatarFlycloakChangeNotify(GenshinAvatar avatar) {
		super(PacketOpcodes.AvatarFlycloakChangeNotify);

		 AvatarFlycloakChangeNotify proto = AvatarFlycloakChangeNotify.newBuilder()
				 .setAvatarGuid(avatar.getGuid())
				 .setFlycloakId(avatar.getFlyCloak())
				 .build();

		 this.setData(proto);
	}
}
