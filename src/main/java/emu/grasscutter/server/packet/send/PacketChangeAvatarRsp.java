package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChangeAvatarRspOuterClass.ChangeAvatarRsp;

public class PacketChangeAvatarRsp extends GenshinPacket {
	
	public PacketChangeAvatarRsp(long guid) {
		super(PacketOpcodes.ChangeAvatarRsp);

		ChangeAvatarRsp p = ChangeAvatarRsp.newBuilder()
				.setRetcode(0)
				.setCurrGuid(guid)
				.build();
		
		this.setData(p);
	}
}
