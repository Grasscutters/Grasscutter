package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TowerFloorRecordChangeNotifyOuterClass.TowerFloorRecordChangeNotify;
import emu.grasscutter.net.proto.TowerFloorRecordOuterClass.TowerFloorRecord;
import emu.grasscutter.net.proto.TowerLevelRecordOuterClass.TowerLevelRecord;

public class PacketTowerFloorRecordChangeNotify extends BasePacket {

	public PacketTowerFloorRecordChangeNotify(int floorId) {
		super(PacketOpcodes.TowerFloorRecordChangeNotify);

		TowerFloorRecordChangeNotify proto = TowerFloorRecordChangeNotify.newBuilder()
				.addTowerFloorRecordList(TowerFloorRecord.newBuilder()
						.setFloorId(floorId)
						.setFloorStarRewardProgress(3)
						.addPassedLevelRecordList(TowerLevelRecord.newBuilder()
								.setLevelId(1)
								.addSatisfiedCondList(1)
								.addSatisfiedCondList(2)
								.addSatisfiedCondList(3)
								.build())
						.build())
				.setIsFinishedEntranceFloor(true)
				.build();
		
		this.setData(proto);
	}
}
