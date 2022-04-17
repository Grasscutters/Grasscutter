package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetEquipLockStateRspOuterClass.SetEquipLockStateRsp;

public class PacketSetEquipLockStateRsp extends GenshinPacket {
	
	public PacketSetEquipLockStateRsp(GenshinItem equip) {
		super(PacketOpcodes.SetEquipLockStateRsp);
		
		this.buildHeader(0);

		SetEquipLockStateRsp proto = SetEquipLockStateRsp.newBuilder()
				.setTargetEquipGuid(equip.getGuid())
				.setIsLocked(equip.isLocked())
				.build();
		
		this.setData(proto);
	}
}
