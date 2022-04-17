package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarSkillChangeNotifyOuterClass.AvatarSkillChangeNotify;

public class PacketAvatarSkillChangeNotify extends GenshinPacket {
	
	public PacketAvatarSkillChangeNotify(GenshinAvatar avatar, int skillId, int oldLevel, int curLevel) {
		super(PacketOpcodes.AvatarSkillChangeNotify);
		
		AvatarSkillChangeNotify proto = AvatarSkillChangeNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setEntityId(avatar.getEntityId())
				.setSkillDepotId(avatar.getSkillDepotId())
				.setAvatarSkillId(skillId)
				.setOldLevel(oldLevel)
				.setCurLevel(curLevel)
				.build();
		
		this.setData(proto);
	}
}
