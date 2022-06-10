package emu.grasscutter.server.packet.send;

import java.util.List;
import java.util.Map;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ForgeQueueDataNotifyOuterClass.ForgeQueueDataNotify;
import emu.grasscutter.net.proto.ForgeQueueDataOuterClass.ForgeQueueData;

public class PacketForgeQueueDataNotify extends BasePacket {
	
	public PacketForgeQueueDataNotify(Map<Integer, ForgeQueueData> queueData, List<Integer> removedQueues) {
		super(PacketOpcodes.ForgeQueueDataNotify);
		
		ForgeQueueDataNotify.Builder builder = ForgeQueueDataNotify.newBuilder()
			.addAllRemovedForgeQueueList(removedQueues);

		for (int queueId : queueData.keySet()) {
			var data = queueData.get(queueId);
			builder.putForgeQueueMap(queueId, data);
		}

		this.setData(builder.build());
	}
}
