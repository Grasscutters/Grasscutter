package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.game.gacha.GachaBanner;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DoGachaRspOuterClass.DoGachaRsp;
import emu.grasscutter.net.proto.GachaItemOuterClass.GachaItem;

public class PacketDoGachaRsp extends GenshinPacket {
	
	public PacketDoGachaRsp(GachaBanner banner, List<GachaItem> list) {
		super(PacketOpcodes.DoGachaRsp);

		DoGachaRsp p = DoGachaRsp.newBuilder()
				.setGachaType(banner.getGachaType())
				.setGachaScheduleId(banner.getScheduleId())
				.setGachaTimes(list.size())
				.setNewGachaRandom(12345)
				.setLeftGachaTimes(Integer.MAX_VALUE)
				.setCostItemId(banner.getCostItem())
	            .setCostItemNum(1)
	            .setTenCostItemId(banner.getCostItem())
	            .setTenCostItemNum(10)
	            .addAllGachaItemList(list)
				.build();
		
		this.setData(p);
	}

	public PacketDoGachaRsp() {
		super(PacketOpcodes.DoGachaRsp);

		DoGachaRsp p = DoGachaRsp.newBuilder()
				.setRetcode(1)
				.build();
		
		this.setData(p);
	}
}
