package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ForgeDataNotifyOuterClass.ForgeDataNotify;

public class PacketForgeDataNotify extends BasePacket {
	
	public PacketForgeDataNotify(Iterable<Integer> unlockedItem, int numQueues) {
		super(PacketOpcodes.ForgeDataNotify);
		
		/*int adventureRank = player.getLevel();
		int numQueues = 
			(adventureRank >= 15) ? 4 
			: (adventureRank >= 10) ? 3 
			: (adventureRank >= 5) ? 2 
			: 1;*/

		ForgeDataNotify proto = ForgeDataNotify.newBuilder()
			.addAllForgeIdList(unlockedItem)
			.setMaxQueueNum(numQueues)
			.build();

		// ToDo: Add the information for the actual forging queues
		// and ongoing forges.

		this.setData(proto);
	}
}
