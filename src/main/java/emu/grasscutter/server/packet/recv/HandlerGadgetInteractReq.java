package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.GadgetInteractReq)
public class HandlerGadgetInteractReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		GadgetInteractReq req = GadgetInteractReq.parseFrom(payload);
		
		session.getPlayer().interactWith(req.getGadgetEntityId());
	}

}
