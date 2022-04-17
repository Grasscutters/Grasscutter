package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ProudSkillChangeNotifyOuterClass.ProudSkillChangeNotify;

public class PacketProudSkillChangeNotify extends GenshinPacket {
	
	public PacketProudSkillChangeNotify(GenshinAvatar avatar) {
		super(PacketOpcodes.ProudSkillChangeNotify);
		
		ProudSkillChangeNotify proto = ProudSkillChangeNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setEntityId(avatar.getEntityId())
				.setSkillDepotId(avatar.getSkillDepotId())
				.addAllProudSkillList(avatar.getProudSkillList())
				.build();
		
		this.setData(proto);
	}
}
