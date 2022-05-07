package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarFightPropNotifyOuterClass.AvatarFightPropNotify;

public class PacketAvatarFightPropNotify extends BasePacket {
	
	public PacketAvatarFightPropNotify(Avatar avatar) {
		super(PacketOpcodes.AvatarFightPropNotify);
		
		AvatarFightPropNotify proto = AvatarFightPropNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.putAllFightPropMap(avatar.getFightProperties())
				.build();
		
		this.setData(proto);
	}
}
