package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.PlayerEnterDungeonReqOuterClass.PlayerEnterDungeonReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.PlayerEnterDungeonReq)
public class HandlerPlayerEnterDungeonReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		// Auto template
		PlayerEnterDungeonReq req = PlayerEnterDungeonReq.parseFrom(payload);
		
		session.getServer().getDungeonManager().enterDungeon(session.getPlayer(), req.getPointId(), req.getDungeonId());
	}

}
