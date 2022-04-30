package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.GetPlayerBlacklistReq)
public class HandlerGetPlayerBlacklistReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		session.send(new BasePacket(PacketOpcodes.GetPlayerBlacklistRsp).buildHeader(3));
	}

}
