package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetAuthkeyRspOuterClass.GetAuthkeyRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketGetAuthkeyRsp extends GenshinPacket {
	
	public PacketGetAuthkeyRsp() {
		super(PacketOpcodes.GetAuthkeyRsp);
		
		GetAuthkeyRsp proto = GetAuthkeyRsp.newBuilder().setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE).build();
		
		this.setData(proto);
	}
}
