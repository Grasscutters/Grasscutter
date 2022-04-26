package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChangeAvatarRspOuterClass.ChangeAvatarRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketChangeAvatarRsp extends GenshinPacket {
	
	public PacketChangeAvatarRsp(long guid) {
		super(PacketOpcodes.ChangeAvatarRsp);

		ChangeAvatarRsp p = ChangeAvatarRsp.newBuilder()
				.setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE)
				.setCurGuid(guid)
				.build();
		
		this.setData(p);
	}
}
