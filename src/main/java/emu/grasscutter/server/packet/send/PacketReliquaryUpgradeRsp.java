package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ReliquaryUpgradeRspOuterClass.ReliquaryUpgradeRsp;

public class PacketReliquaryUpgradeRsp extends GenshinPacket {
	
	public PacketReliquaryUpgradeRsp(GenshinItem relic, int rate, int oldLevel, List<Integer> oldAppendPropIdList) {
		super(PacketOpcodes.ReliquaryUpgradeRsp);

		ReliquaryUpgradeRsp proto = ReliquaryUpgradeRsp.newBuilder()
				.setTargetReliquaryGuid(relic.getGuid())
				.setOldLevel(oldLevel)
				.setCurLevel(relic.getLevel())
				.setPowerUpRate(rate)
				.addAllOldAppendPropList(oldAppendPropIdList)
				.addAllCurAppendPropList(relic.getAppendPropIdList())
				.build();
		
		this.setData(proto);
	}
}
