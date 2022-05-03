package emu.grasscutter.server.packet.send;

import java.util.Arrays;

import emu.grasscutter.data.common.PointData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DungeonEntryInfoOuterClass.DungeonEntryInfo;
import emu.grasscutter.net.proto.DungeonEntryInfoRspOuterClass.DungeonEntryInfoRsp;

public class PacketDungeonEntryInfoRsp extends BasePacket {
	
	public PacketDungeonEntryInfoRsp(Player player, PointData pointData) {
		super(PacketOpcodes.DungeonEntryInfoRsp);

		DungeonEntryInfoRsp.Builder proto = DungeonEntryInfoRsp.newBuilder()
				.setPointId(pointData.getId());
		
		if (pointData.getDungeonIds() != null) {
			for (int dungeonId : pointData.getDungeonIds()) {
				DungeonEntryInfo info = DungeonEntryInfo.newBuilder().setDungeonId(dungeonId).build();
				proto.addDungeonEntryList(info);
			}
		}

		this.setData(proto);
	}
	
	public PacketDungeonEntryInfoRsp() {
		super(PacketOpcodes.DungeonEntryInfoRsp);

		DungeonEntryInfoRsp proto = DungeonEntryInfoRsp.newBuilder()
				.setRetcode(1)
				.build();
		
		this.setData(proto);
	}
}
