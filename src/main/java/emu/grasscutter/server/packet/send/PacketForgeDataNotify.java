package emu.grasscutter.server.packet.send;

import java.util.Map;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ForgeDataNotifyOuterClass.ForgeDataNotify;
import emu.grasscutter.net.proto.ForgeQueueDataOuterClass.ForgeQueueData;

public class PacketForgeDataNotify extends BasePacket {
	
	public PacketForgeDataNotify(Iterable<Integer> unlockedItem, int numQueues, Map<Integer, ForgeQueueData> queueData) {
		super(PacketOpcodes.ForgeDataNotify);
		
		ForgeDataNotify.Builder builder = ForgeDataNotify.newBuilder()
			.addAllForgeIdList(unlockedItem)
			.setMaxQueueNum(numQueues);

		for (int queueId : queueData.keySet()) {
			var data = queueData.get(queueId);
			builder.putForgeQueueMap(queueId, data);
		}

		this.setData(builder.build());
	}
}
