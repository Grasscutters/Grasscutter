package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarFightPropUpdateNotifyOuterClass.AvatarFightPropUpdateNotify;

public class PacketAvatarFightPropUpdateNotify extends BasePacket {
	
	public PacketAvatarFightPropUpdateNotify(Avatar avatar, FightProperty prop) {
		super(PacketOpcodes.AvatarFightPropUpdateNotify);
		
		AvatarFightPropUpdateNotify proto = AvatarFightPropUpdateNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.putFightPropMap(prop.getId(), avatar.getFightProperty(prop))
				.build();
		
		this.setData(proto);
	}
}
