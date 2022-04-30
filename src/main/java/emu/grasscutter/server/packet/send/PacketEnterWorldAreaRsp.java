package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EnterWorldAreaReqOuterClass.EnterWorldAreaReq;
import emu.grasscutter.net.proto.EnterWorldAreaRspOuterClass.EnterWorldAreaRsp;

public class PacketEnterWorldAreaRsp extends BasePacket {

	public PacketEnterWorldAreaRsp(int clientSequence, EnterWorldAreaReq enterWorld) {
		super(PacketOpcodes.EnterWorldAreaRsp, clientSequence);
		
		EnterWorldAreaRsp p = EnterWorldAreaRsp.newBuilder()
				.setAreaType(enterWorld.getAreaType())
				.setAreaId(enterWorld.getAreaId())
				.build();
		
		this.setData(p.toByteArray());
	}
}
