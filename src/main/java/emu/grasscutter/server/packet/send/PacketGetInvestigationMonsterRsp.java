package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetActivityInfoRspOuterClass;

import java.util.List;

public class PacketGetInvestigationMonsterRsp extends BasePacket {

	public PacketGetInvestigationMonsterRsp(List<Integer> cityIdListList) {
		super(PacketOpcodes.GetInvestigationMonsterRsp);

		var resp = GetActivityInfoRspOuterClass.GetActivityInfoRsp.newBuilder();



		this.setData(resp.build());
	}
}
