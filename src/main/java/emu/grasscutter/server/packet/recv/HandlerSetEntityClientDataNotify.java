package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.SetEntityClientDataNotify)
public class HandlerSetEntityClientDataNotify extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		// Skip if there is no one to broadcast it too
		if (session.getPlayer().getScene().getPlayerCount() <= 1) {
			return;
		}
		
		GenshinPacket packet = new GenshinPacket(PacketOpcodes.SetEntityClientDataNotify, true);
		packet.setData(payload);
		
		session.getPlayer().getScene().broadcastPacketToOthers(session.getPlayer(), packet);
	}

}
