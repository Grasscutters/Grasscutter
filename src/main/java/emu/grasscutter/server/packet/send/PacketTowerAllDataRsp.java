package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.def.TowerFloorData;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TowerAllDataRspOuterClass.TowerAllDataRsp;
import emu.grasscutter.net.proto.TowerCurLevelRecordOuterClass.TowerCurLevelRecord;
import emu.grasscutter.net.proto.TowerFloorRecordOuterClass.TowerFloorRecord;

import java.util.stream.Collectors;

public class PacketTowerAllDataRsp extends BasePacket {
	
	public PacketTowerAllDataRsp() {
		super(PacketOpcodes.TowerAllDataRsp);

		var list = GameData.getTowerFloorDataMap().values().stream()
				.map(TowerFloorData::getFloorId)
				.map(id -> TowerFloorRecord.newBuilder().setFloorId(id).build())
				.collect(Collectors.toList());

		TowerAllDataRsp proto = TowerAllDataRsp.newBuilder()
				.setTowerScheduleId(29)
				.addAllTowerFloorRecordList(list)
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
