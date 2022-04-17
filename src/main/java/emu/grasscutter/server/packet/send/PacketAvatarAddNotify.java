package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarAddNotifyOuterClass.AvatarAddNotify;

public class PacketAvatarAddNotify extends GenshinPacket {
	
	public PacketAvatarAddNotify(GenshinAvatar avatar, boolean addedToTeam) {
		super(PacketOpcodes.AvatarAddNotify);
		
		AvatarAddNotify proto = AvatarAddNotify.newBuilder()
				.setAvatar(avatar.toProto())
				.setIsInTeam(addedToTeam)
				.build();
		
		this.setData(proto);
	}
}
