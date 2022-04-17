package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarFightPropNotifyOuterClass.AvatarFightPropNotify;

public class PacketAvatarFightPropNotify extends GenshinPacket {
	
	public PacketAvatarFightPropNotify(GenshinAvatar avatar) {
		super(PacketOpcodes.AvatarFightPropNotify);
		
		AvatarFightPropNotify proto = AvatarFightPropNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.putAllFightPropMap(avatar.getFightProperties())
				.build();
		
		this.setData(proto);
	}
}
