package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AddQuestContentProgressRspOuterClass;

public class PacketAddQuestContentProgressRsp extends BasePacket {

	public PacketAddQuestContentProgressRsp(int contentType) {
		super(PacketOpcodes.AddQuestContentProgressRsp);

        var proto = AddQuestContentProgressRspOuterClass.AddQuestContentProgressRsp.newBuilder();

        proto.setContentType(contentType);

        this.setData(proto);

	}
}
