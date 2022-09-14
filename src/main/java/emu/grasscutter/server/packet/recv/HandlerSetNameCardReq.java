package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SetNameCardReqOuterClass.SetNameCardReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.SetNameCardReq)
public class HandlerSetNameCardReq extends PacketHandler {
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		SetNameCardReq req = SetNameCardReq.parseFrom(payload);
		
		session.getPlayer().setNameCard(req.getNameCardId());
	}
}
