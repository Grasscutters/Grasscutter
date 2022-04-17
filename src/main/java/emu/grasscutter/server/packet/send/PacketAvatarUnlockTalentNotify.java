package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarUnlockTalentNotifyOuterClass.AvatarUnlockTalentNotify;

public class PacketAvatarUnlockTalentNotify extends GenshinPacket {
	
	public PacketAvatarUnlockTalentNotify(GenshinAvatar avatar, int talentId) {
		super(PacketOpcodes.AvatarUnlockTalentNotify);

		AvatarUnlockTalentNotify proto = AvatarUnlockTalentNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setEntityId(avatar.getEntityId())
				.setTalentId(talentId)
				.setSkillDepotId(avatar.getSkillDepotId())
				.build();
		
		this.setData(proto);
	}
}
