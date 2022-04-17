package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarFightPropUpdateNotifyOuterClass.AvatarFightPropUpdateNotify;

public class PacketAvatarFightPropUpdateNotify extends GenshinPacket {
	
	public PacketAvatarFightPropUpdateNotify(GenshinAvatar avatar, FightProperty prop) {
		super(PacketOpcodes.AvatarFightPropUpdateNotify);
		
		AvatarFightPropUpdateNotify proto = AvatarFightPropUpdateNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.putFightPropMap(prop.getId(), avatar.getFightProperty(prop))
				.build();
		
		this.setData(proto);
	}
}
