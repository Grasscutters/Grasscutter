package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetUpAvatarTeamReqOuterClass.SetUpAvatarTeamReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.SetUpAvatarTeamReq)
public class HandlerSetUpAvatarTeamReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		SetUpAvatarTeamReq req = SetUpAvatarTeamReq.parseFrom(payload);
		
		session.getPlayer().getTeamManager().setupAvatarTeam(req.getTeamId(), req.getAvatarTeamGuidListList());
	}

}
