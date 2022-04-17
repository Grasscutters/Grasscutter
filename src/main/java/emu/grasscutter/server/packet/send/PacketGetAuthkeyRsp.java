package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetAuthkeyRspOuterClass.GetAuthkeyRsp;

public class PacketGetAuthkeyRsp extends GenshinPacket {
	
	public PacketGetAuthkeyRsp() {
		super(PacketOpcodes.GetAuthkeyRsp);
		
		GetAuthkeyRsp proto = GetAuthkeyRsp.newBuilder().setRetcode(1).build();
		
		this.setData(proto);
	}
}
