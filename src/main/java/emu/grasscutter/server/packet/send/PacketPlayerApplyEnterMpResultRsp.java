package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerApplyEnterMpResultRspOuterClass.PlayerApplyEnterMpResultRsp;

public class PacketPlayerApplyEnterMpResultRsp extends BasePacket {
	
	public PacketPlayerApplyEnterMpResultRsp(int applyUid, boolean isAgreed) {
		super(PacketOpcodes.PlayerApplyEnterMpResultRsp);

		PlayerApplyEnterMpResultRsp proto = PlayerApplyEnterMpResultRsp.newBuilder()
				.setApplyUid(applyUid)
				.setIsAgreed(isAgreed)
				.build();
		
		this.setData(proto);
	}
}
