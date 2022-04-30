package emu.grasscutter.server.packet.send;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WeaponPromoteRspOuterClass.WeaponPromoteRsp;

public class PacketWeaponPromoteRsp extends BasePacket {
	
	public PacketWeaponPromoteRsp(GameItem item, int oldPromoteLevel) {
		super(PacketOpcodes.WeaponPromoteRsp);

		WeaponPromoteRsp proto = WeaponPromoteRsp.newBuilder()
				.setTargetWeaponGuid(item.getGuid())
				.setCurPromoteLevel(item.getPromoteLevel())
				.setOldPromoteLevel(oldPromoteLevel)
				.build();
		
		this.setData(proto);
	}
}
