package emu.grasscutter.server.packet.send;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetShopmallDataRspOuterClass.GetShopmallDataRsp;

import java.util.List;

public class PacketGetShopmallDataRsp extends BasePacket {
	
	public PacketGetShopmallDataRsp() {
		super(PacketOpcodes.GetShopmallDataRsp);

		List<Integer> shop_malls = List.of(900, 1052, 902, 1001, 903);

		GetShopmallDataRsp proto = GetShopmallDataRsp.newBuilder()
				.addAllShopTypeList(shop_malls)
				.build();

		this.setData(proto);
	}
}
