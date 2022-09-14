package emu.grasscutter.server.packet.send;

import java.util.Map;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ForgeGetQueueDataRspOuterClass.ForgeGetQueueDataRsp;
import emu.grasscutter.net.proto.ForgeQueueDataOuterClass.ForgeQueueData;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public class PacketForgeGetQueueDataRsp extends BasePacket {
	
	public PacketForgeGetQueueDataRsp(Retcode retcode, int numQueues, Map<Integer, ForgeQueueData> queueData) {
		super(PacketOpcodes.ForgeGetQueueDataRsp);

		ForgeGetQueueDataRsp.Builder builder = ForgeGetQueueDataRsp.newBuilder()
			.setRetcode(retcode.getNumber())
			.setMaxQueueNum(numQueues);

			for (int queueId : queueData.keySet()) {
				var data = queueData.get(queueId);
				builder.putForgeQueueMap(queueId, data);
			}

		this.setData(builder.build());
	}
}
