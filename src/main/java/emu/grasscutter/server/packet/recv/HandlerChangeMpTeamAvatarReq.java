package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChangeMpTeamAvatarReqOuterClass.ChangeMpTeamAvatarReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ChangeMpTeamAvatarReq)
public class HandlerChangeMpTeamAvatarReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		ChangeMpTeamAvatarReq req = ChangeMpTeamAvatarReq.parseFrom(payload);
		
		session.getPlayer().getTeamManager().setupMpTeam(req.getAvatarGuidListList());
	}

}
