package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.DoGachaReqOuterClass.DoGachaReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.DoGachaReq)
public class HandlerDoGachaReq extends PacketHandler {
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		DoGachaReq req = DoGachaReq.parseFrom(payload);
		
		session.getServer().getGachaManager().doPulls(session.getPlayer(), req.getGachaType(), req.getGachaTimes());
	}
}
