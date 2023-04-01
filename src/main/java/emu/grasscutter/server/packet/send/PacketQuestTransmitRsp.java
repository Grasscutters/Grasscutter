package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.net.proto.QuestTransmitRspOuterClass.QuestTransmitRsp;
import emu.grasscutter.net.proto.QuestTransmitReqOuterClass.QuestTransmitReq;

public class PacketQuestTransmitRsp extends BasePacket {

	public PacketQuestTransmitRsp(boolean result, QuestTransmitReq req) {
		super(PacketOpcodes.QuestTransmitRsp);
		this.setData(QuestTransmitRsp.newBuilder()
			.setQuestId(req.getQuestId())
			.setPointId(req.getPointId())
			.setRetcode(result ? Retcode.RET_SUCC_VALUE : Retcode.RET_FAIL_VALUE));
	}
}
