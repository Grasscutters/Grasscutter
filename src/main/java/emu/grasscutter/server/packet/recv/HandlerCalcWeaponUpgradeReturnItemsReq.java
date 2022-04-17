package emu.grasscutter.server.packet.recv;

import java.util.List;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.CalcWeaponUpgradeReturnItemsReqOuterClass.CalcWeaponUpgradeReturnItemsReq;
import emu.grasscutter.net.proto.ItemParamOuterClass.ItemParam;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketCalcWeaponUpgradeReturnItemsRsp;

@Opcodes(PacketOpcodes.CalcWeaponUpgradeReturnItemsReq)
public class HandlerCalcWeaponUpgradeReturnItemsReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		CalcWeaponUpgradeReturnItemsReq req = CalcWeaponUpgradeReturnItemsReq.parseFrom(payload);
		
		List<ItemParam> returnOres = session.getServer().getInventoryManager().calcWeaponUpgradeReturnItems(
				session.getPlayer(), 
				req.getTargetWeaponGuid(), 
				req.getFoodWeaponGuidListList(),
				req.getItemParamListList()
		);
		
		if (returnOres != null) {
			session.send(new PacketCalcWeaponUpgradeReturnItemsRsp(req.getTargetWeaponGuid(), returnOres));
		} else {
			session.send(new PacketCalcWeaponUpgradeReturnItemsRsp());
		}
	}

}
