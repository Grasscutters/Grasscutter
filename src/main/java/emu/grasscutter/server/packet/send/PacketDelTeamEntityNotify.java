package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DelTeamEntityNotifyOuterClass.DelTeamEntityNotify;

public class PacketDelTeamEntityNotify extends BasePacket {
	
	public PacketDelTeamEntityNotify(int sceneId, int teamEntityId) {
		super(PacketOpcodes.DelTeamEntityNotify);

		DelTeamEntityNotify proto = DelTeamEntityNotify.newBuilder()
				.setSceneId(sceneId)
				.addDelEntityIdList(teamEntityId)
				.build();
		
		this.setData(proto);
	}
	
	public PacketDelTeamEntityNotify(int sceneId, List<Integer> list) {
		super(PacketOpcodes.DelTeamEntityNotify);

		DelTeamEntityNotify proto = DelTeamEntityNotify.newBuilder()
				.setSceneId(sceneId)
				.addAllDelEntityIdList(list)
				.build();
		
		this.setData(proto);
	}
}
