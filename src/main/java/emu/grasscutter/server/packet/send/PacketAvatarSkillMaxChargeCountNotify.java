package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarSkillMaxChargeCountNotifyOuterClass.AvatarSkillMaxChargeCountNotify;

public class PacketAvatarSkillMaxChargeCountNotify extends BasePacket {
	
	public PacketAvatarSkillMaxChargeCountNotify(Avatar avatar, int skillId, int maxCharges) {
		super(PacketOpcodes.AvatarSkillMaxChargeCountNotify);

		AvatarSkillMaxChargeCountNotify proto = AvatarSkillMaxChargeCountNotify.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setSkillId(skillId)
				.setMaxChargeCount(maxCharges)
				.build();
		
		this.setData(proto);
	}
}
