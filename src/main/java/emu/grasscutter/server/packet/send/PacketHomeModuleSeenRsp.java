package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeModuleSeenRspOuterClass.HomeModuleSeenRsp;

public class PacketHomeModuleSeenRsp extends BasePacket {

	public PacketHomeModuleSeenRsp(List<Integer> seen) {
		super(PacketOpcodes.HomeModuleSeenRsp);

		HomeModuleSeenRsp proto = HomeModuleSeenRsp.newBuilder()
				.addAllSeenModuleIdList(seen)
				.build();

		this.setData(proto);
	}
}
