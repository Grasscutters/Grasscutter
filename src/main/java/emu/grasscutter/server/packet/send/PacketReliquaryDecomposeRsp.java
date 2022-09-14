package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ReliquaryDecomposeRspOuterClass.ReliquaryDecomposeRsp;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;

public class PacketReliquaryDecomposeRsp extends BasePacket {
	public PacketReliquaryDecomposeRsp(Retcode retcode) {
		super(PacketOpcodes.ReliquaryDecomposeRsp);

		ReliquaryDecomposeRsp proto = ReliquaryDecomposeRsp.newBuilder()
				.setRetcode(retcode.getNumber())
				.build();
		
		this.setData(proto);
	}

	public PacketReliquaryDecomposeRsp(List<Long> output) {
		super(PacketOpcodes.ReliquaryDecomposeRsp);

		ReliquaryDecomposeRsp proto = ReliquaryDecomposeRsp.newBuilder()
				.addAllGuidList(output)
				.build();
		
		this.setData(proto);
	}
}
