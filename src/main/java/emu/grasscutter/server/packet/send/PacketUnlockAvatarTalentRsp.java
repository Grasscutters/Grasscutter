package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.UnlockAvatarTalentRspOuterClass.UnlockAvatarTalentRsp;

public class PacketUnlockAvatarTalentRsp extends GenshinPacket {
	
	public PacketUnlockAvatarTalentRsp(GenshinAvatar avatar, int talentId) {
		super(PacketOpcodes.UnlockAvatarTalentRsp);

		UnlockAvatarTalentRsp proto = UnlockAvatarTalentRsp.newBuilder()
				.setAvatarGuid(avatar.getGuid())
				.setTalentId(talentId)
				.build();
		
		this.setData(proto);
	}
}
