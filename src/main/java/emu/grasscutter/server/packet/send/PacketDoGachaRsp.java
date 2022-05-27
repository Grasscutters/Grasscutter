package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.gacha.GachaBanner;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DoGachaRspOuterClass.DoGachaRsp;
import emu.grasscutter.net.proto.GachaItemOuterClass.GachaItem;
import emu.grasscutter.net.proto.RetcodeOuterClass;

public class PacketDoGachaRsp extends BasePacket {
	
	public PacketDoGachaRsp(GachaBanner banner, List<GachaItem> list) {
		super(PacketOpcodes.DoGachaRsp);

		ItemParamData costItem = banner.getCost(1);
		ItemParamData costItem10 = banner.getCost(10);
		DoGachaRsp p = DoGachaRsp.newBuilder()
				.setGachaType(banner.getGachaType())
				.setGachaScheduleId(banner.getScheduleId())
				.setGachaTimes(list.size())
				.setNewGachaRandom(12345)
				.setLeftGachaTimes(Integer.MAX_VALUE)
				.setCostItemId(costItem.getId())
	            .setCostItemNum(costItem.getCount())
	            .setTenCostItemId(costItem10.getId())
	            .setTenCostItemNum(costItem10.getCount())
	            .addAllGachaItemList(list)
				.build();
		
		this.setData(p);
	}

	public PacketDoGachaRsp() {
		super(PacketOpcodes.DoGachaRsp);

		DoGachaRsp p = DoGachaRsp.newBuilder()
				.setRetcode(RetcodeOuterClass.Retcode.RET_SVR_ERROR_VALUE)
				.build();
		
		this.setData(p);
	}
}
