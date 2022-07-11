package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketUnk2700_KIIOGMKFNNP_ServerRsp;

@Opcodes(PacketOpcodes.Unk2700_ACILPONNGGK_ClientReq)
public class Unk2700_ACILPONNGGK_ClientReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		/*
		 * This packet is about the edit mode
		 */
		session.send(new PacketUnk2700_KIIOGMKFNNP_ServerRsp());
	}

}
