package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarPromoteRspOuterClass.AvatarPromoteRsp;

public class PacketAvatarPromoteRsp extends BasePacket {
	
	public PacketAvatarPromoteRsp(Avatar avatar) {
		super(PacketOpcodes.AvatarPromoteRsp);

		AvatarPromoteRsp proto = AvatarPromoteRsp.newBuilder()
				.setGuid(avatar.getGuid())
				.build();

		this.setData(proto);
	}
}
