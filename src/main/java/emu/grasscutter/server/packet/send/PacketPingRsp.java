package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PingRspOuterClass.PingRsp;

public class PacketPingRsp extends BasePacket {

	public PacketPingRsp(int clientSeq, int time) {
		super(PacketOpcodes.PingRsp, clientSeq);
		
		PingRsp p = PingRsp.newBuilder()
				.setClientTime(time)
				.build();
		
		this.setData(p.toByteArray());
	}
}
