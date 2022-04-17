package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GenshinItem;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WeaponPromoteRspOuterClass.WeaponPromoteRsp;

public class PacketWeaponPromoteRsp extends GenshinPacket {
	
	public PacketWeaponPromoteRsp(GenshinItem item, int oldPromoteLevel) {
		super(PacketOpcodes.WeaponPromoteRsp);

		WeaponPromoteRsp proto = WeaponPromoteRsp.newBuilder()
				.setTargetWeaponGuid(item.getGuid())
				.setCurPromoteLevel(item.getPromoteLevel())
				.setOldPromoteLevel(oldPromoteLevel)
				.build();
		
		this.setData(proto);
	}
}
