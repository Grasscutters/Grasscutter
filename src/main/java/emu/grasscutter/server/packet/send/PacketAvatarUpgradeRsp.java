package emu.grasscutter.server.packet.send;

import java.util.Map;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarUpgradeRspOuterClass.AvatarUpgradeRsp;

public class PacketAvatarUpgradeRsp extends GenshinPacket {
	
	public PacketAvatarUpgradeRsp(GenshinAvatar avatar, int oldLevel, Map<Integer, Float> oldFightPropMap) {
		super(PacketOpcodes.AvatarUpgradeRsp);
		
		this.buildHeader(0);

		AvatarUpgradeRsp proto = AvatarUpgradeRsp.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setOldLevel(oldLevel)
				.setCurLevel(avatar.getLevel())
				.putAllOldFightPropMap(oldFightPropMap)
				.putAllCurFightPropMap(avatar.getFightProperties())
				.build();
		
		this.setData(proto);
	}
}
