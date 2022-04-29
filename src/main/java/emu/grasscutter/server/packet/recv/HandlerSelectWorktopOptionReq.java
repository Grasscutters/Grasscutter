package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.SelectWorktopOptionReqOuterClass.SelectWorktopOptionReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSelectWorktopOptionRsp;

@Opcodes(PacketOpcodes.SelectWorktopOptionReq)
public class HandlerSelectWorktopOptionReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		SelectWorktopOptionReq req = SelectWorktopOptionReq.parseFrom(payload);
		
		session.getPlayer().getScene().getScriptManager().onOptionSelect(req.getGadgetEntityId(), req.getOptionId());
		
		session.send(new PacketSelectWorktopOptionRsp(req.getGadgetEntityId(), req.getOptionId()));
	}

}
