package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetScenePointReqOuterClass.GetScenePointReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetScenePointRsp;

@Opcodes(PacketOpcodes.GetScenePointReq)
public class HandlerGetScenePointReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		GetScenePointReq req = GetScenePointReq.parseFrom(payload);
		
		session.send(new PacketGetScenePointRsp(req.getSceneId()));
	}

}
