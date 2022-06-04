package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ForgeGetQueueDataRspOuterClass.ForgeGetQueueDataRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public class PacketForgeGetQueueDataRsp extends BasePacket {
	
	public PacketForgeGetQueueDataRsp(Retcode retcode, int numQueues) {
		super(PacketOpcodes.ForgeGetQueueDataRsp);

		ForgeGetQueueDataRsp proto = ForgeGetQueueDataRsp.newBuilder()
			.setRetcode(retcode.getNumber())
			.setMaxQueueNum(numQueues)
			.build();

		// ToDo: Add the information for the actual forging queues
		// and ongoing forges.

		this.setData(proto);
	}
}
