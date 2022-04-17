package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.proto.WeaponUpgradeRspOuterClass.WeaponUpgradeRsp;

public class PacketWeaponUpgradeRsp extends GenshinPacket {
	
	public PacketWeaponUpgradeRsp(GenshinItem item, int oldLevel, List<ItemParam> leftoverOres) {
		super(PacketOpcodes.WeaponUpgradeRsp);
		
		WeaponUpgradeRsp proto = WeaponUpgradeRsp.newBuilder()
				.setTargetWeaponGuid(item.getGuid())
				.setCurLevel(item.getLevel())
				.setOldLevel(oldLevel)
				.addAllItemParamList(leftoverOres)
				.build();
		
		this.setData(proto);
	}
}
