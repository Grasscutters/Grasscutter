package emu.grasscutter.server.packet.send;

import java.util.List;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.Retcode;
import emu.grasscutter.net.proto.CalcWeaponUpgradeReturnItemsRspOuterClass.CalcWeaponUpgradeReturnItemsRsp;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;

public class PacketCalcWeaponUpgradeReturnItemsRsp extends GenshinPacket {
	
	public PacketCalcWeaponUpgradeReturnItemsRsp(long itemGuid, List<ItemParam> returnItems) {
		super(PacketOpcodes.CalcWeaponUpgradeReturnItemsRsp);
		
		CalcWeaponUpgradeReturnItemsRsp proto = CalcWeaponUpgradeReturnItemsRsp.newBuilder()
				.setTargetWeaponGuid(itemGuid)
				.addAllItemParamList(returnItems)
				.build();
		
		this.setData(proto);
	}
	
	public PacketCalcWeaponUpgradeReturnItemsRsp() {
		super(PacketOpcodes.CalcWeaponUpgradeReturnItemsRsp);
		
		CalcWeaponUpgradeReturnItemsRsp proto = CalcWeaponUpgradeReturnItemsRsp.newBuilder()
				.setRetcode(Retcode.FAIL)
				.build();
		
		this.setData(proto);
	}
}
