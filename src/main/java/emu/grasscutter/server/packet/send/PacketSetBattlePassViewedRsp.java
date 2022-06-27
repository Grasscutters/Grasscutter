package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetBattlePassViewedRspOuterClass.SetBattlePassViewedRsp;

public class PacketSetBattlePassViewedRsp extends BasePacket {
	
	public PacketSetBattlePassViewedRsp(int scheduleId) {
		super(PacketOpcodes.SetBattlePassViewedRsp);
		
		SetBattlePassViewedRsp proto = SetBattlePassViewedRsp.newBuilder()
				.setScheduleId(scheduleId)
				.build();
		
		this.setData(proto);
	}
}
