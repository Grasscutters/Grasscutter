package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetGachaInfoRsp;

@Opcodes(PacketOpcodes.GetGachaInfoReq)
public class HandlerGetGachaInfoReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		session.send(new PacketGetGachaInfoRsp(session.getServer().getGachaManager(),
					// TODO: use other Nonce/key insteadof session key to ensure the overall security for the player
					session.getPlayer().getAccount().getSessionKey())
					);
	}

}
