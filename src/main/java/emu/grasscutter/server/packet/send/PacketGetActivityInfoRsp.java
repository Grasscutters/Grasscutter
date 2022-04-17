package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetActivityInfoRspOuterClass.GetActivityInfoRsp;

public class PacketGetActivityInfoRsp extends GenshinPacket {
	public PacketGetActivityInfoRsp() {
		super(PacketOpcodes.GetActivityInfoRsp);
		
		GetActivityInfoRsp proto = GetActivityInfoRsp.newBuilder().build();
		
		this.setData(proto);
	}
}
