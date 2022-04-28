package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetActivityInfoRspOuterClass.GetActivityInfoRsp;

public class PacketGetActivityInfoRsp extends BasePacket {
	public PacketGetActivityInfoRsp() {
		super(PacketOpcodes.GetActivityInfoRsp);
		
		GetActivityInfoRsp proto = GetActivityInfoRsp.newBuilder().build();
		
		this.setData(proto);
	}
}
