package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.WeaponUpgradeReqOuterClass.WeaponUpgradeReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.WeaponUpgradeReq)
public class HandlerWeaponUpgradeReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		WeaponUpgradeReq req = WeaponUpgradeReq.parseFrom(payload);
		
		// Level up weapon
		session.getServer().getInventoryManager().upgradeWeapon(
				session.getPlayer(), 
				req.getTargetWeaponGuid(), 
				req.getFoodWeaponGuidListList(),
				req.getItemParamListList()
		);
	}

}
