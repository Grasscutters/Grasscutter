package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarSkillUpgradeRspOuterClass.AvatarSkillUpgradeRsp;

public class PacketAvatarSkillUpgradeRsp extends GenshinPacket {
	
	public PacketAvatarSkillUpgradeRsp(GenshinAvatar avatar, int skillId, int oldLevel, int newLevel) {
		super(PacketOpcodes.AvatarSkillUpgradeRsp);

		AvatarSkillUpgradeRsp proto = AvatarSkillUpgradeRsp.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setAvatarSkillId(skillId)
				.setOldLevel(oldLevel)
				.setCurLevel(newLevel)
				.build();
		
		this.setData(proto);
	}
}
