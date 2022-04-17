package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TowerAllDataRspOuterClass.TowerAllDataRsp;
import emu.grasscutter.net.proto.TowerCurLevelRecordOuterClass.TowerCurLevelRecord;
import emu.grasscutter.net.proto.TowerFloorRecordOuterClass.TowerFloorRecord;

public class PacketTowerAllDataRsp extends GenshinPacket {
	
	public PacketTowerAllDataRsp() {
		super(PacketOpcodes.TowerAllDataRsp);
		
		TowerAllDataRsp proto = TowerAllDataRsp.newBuilder()
				.setTowerScheduleId(29)
				.addTowerFloorRecordList(TowerFloorRecord.newBuilder().setFloorId(1001))
				.setCurLevelRecord(TowerCurLevelRecord.newBuilder().setIsEmpty(true))
				.setNextScheduleChangeTime(Integer.MAX_VALUE)
				.putFloorOpenTimeMap(1024, 1630486800)
				.putFloorOpenTimeMap(1025, 1630486800)
				.putFloorOpenTimeMap(1026, 1630486800)
				.putFloorOpenTimeMap(1027, 1630486800)
				.setScheduleStartTime(1630486800)
				.build();
		
		this.setData(proto);
	}
}
