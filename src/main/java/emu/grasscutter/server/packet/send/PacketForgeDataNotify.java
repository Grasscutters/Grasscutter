package emu.grasscutter.server.packet.send;

import java.util.List;

import dev.morphia.AdvancedDatastore;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ForgeDataNotifyOuterClass.ForgeDataNotify;
import emu.grasscutter.net.proto.ForgeQueueDataOuterClass.ForgeQueueData;

public class PacketForgeDataNotify extends BasePacket {
	
	public PacketForgeDataNotify(Player player) {
		super(PacketOpcodes.ForgeDataNotify);
		
		int adventureRank = player.getLevel();
		int numQueues = 
			(adventureRank >= 15) ? 4 
			: (adventureRank >= 10) ? 3 
			: (adventureRank >= 5) ? 2 
			: 1;

		ForgeDataNotify proto = ForgeDataNotify.newBuilder()
			.addAllForgeIdList(List.of(14017, 14009, 14008))
			.setMaxQueueNum(numQueues)
			.build();

		this.setData(proto);
	}
}
