package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.GenshinAvatar;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarPromoteRspOuterClass.AvatarPromoteRsp;

public class PacketAvatarPromoteRsp extends GenshinPacket {
	
	public PacketAvatarPromoteRsp(GenshinAvatar avatar) {
		super(PacketOpcodes.AvatarPromoteRsp);

		AvatarPromoteRsp proto = AvatarPromoteRsp.newBuilder()
				.setGuid(avatar.getGuid())
				.build();

		this.setData(proto);
	}
}
