package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetShopmallDataRspOuterClass.GetShopmallDataRsp;

public class PacketGetShopmallDataRsp extends GenshinPacket {
	
	public PacketGetShopmallDataRsp() {
		super(PacketOpcodes.GetShopmallDataRsp);

		GetShopmallDataRsp proto = GetShopmallDataRsp.newBuilder().build();
		
		this.setData(proto);
	}
}
