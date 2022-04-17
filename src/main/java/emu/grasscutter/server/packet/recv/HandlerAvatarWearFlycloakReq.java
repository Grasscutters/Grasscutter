package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.AvatarWearFlycloakReqOuterClass.AvatarWearFlycloakReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketAvatarWearFlycloakRsp;

@Opcodes(PacketOpcodes.AvatarWearFlycloakReq)
public class HandlerAvatarWearFlycloakReq extends PacketHandler {
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		AvatarWearFlycloakReq req = AvatarWearFlycloakReq.parseFrom(payload);
		
		boolean success = session.getPlayer().getAvatars().wearFlycloak(req.getAvatarGuid(), req.getFlycloakId());
		
		if (success) {
			session.getPlayer().sendPacket(new PacketAvatarWearFlycloakRsp(req.getAvatarGuid(), req.getFlycloakId()));
		} else {
			session.getPlayer().sendPacket(new PacketAvatarWearFlycloakRsp());
		}
	}
}
