package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChangeGameTimeReqOuterClass.ChangeGameTimeReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketChangeGameTimeRsp;

@Opcodes(PacketOpcodes.ChangeGameTimeReq)
public class HandlerChangeGameTimeReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		ChangeGameTimeReq req = ChangeGameTimeReq.parseFrom(payload);
		
		session.getPlayer().getScene().changeTime(req.getGameTime());
		session.getPlayer().sendPacket(new PacketChangeGameTimeRsp(session.getPlayer()));
	}

}
