package emu.grasscutter.server.packet.send;

import java.util.Map.Entry;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarSkillInfoNotifyOuterClass.AvatarSkillInfoNotify;
import emu.grasscutter.net.proto.AvatarSkillInfoOuterClass.AvatarSkillInfo;

public class PacketAvatarSkillInfoNotify extends BasePacket {
	
	public PacketAvatarSkillInfoNotify(Avatar avatar) {
		super(PacketOpcodes.AvatarSkillInfoNotify);

		AvatarSkillInfoNotify.Builder proto = AvatarSkillInfoNotify.newBuilder()
				.setGuid(avatar.getGuid());
		
		for (Entry<Integer, Integer> entry : avatar.getSkillExtraChargeMap().entrySet()) {
			proto.putSkillMap(entry.getKey(), AvatarSkillInfo.newBuilder().setMaxChargeCount(entry.getValue()).build());
		}
		
		this.setData(proto);
	}
}
