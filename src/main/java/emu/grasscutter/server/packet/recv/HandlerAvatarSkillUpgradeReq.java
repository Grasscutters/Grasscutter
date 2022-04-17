package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarSkillUpgradeReqOuterClass.AvatarSkillUpgradeReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.AvatarSkillUpgradeReq)
public class HandlerAvatarSkillUpgradeReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		AvatarSkillUpgradeReq req = AvatarSkillUpgradeReq.parseFrom(payload);
		
		// Level up avatar talent
		session.getServer().getInventoryManager().upgradeAvatarSkill(session.getPlayer(), req.getAvatarGuid(), req.getAvatarSkillId());
	}

}
