package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.EnterWorldAreaReqOuterClass.EnterWorldAreaReq;
import emu.grasscutter.net.proto.PacketHeadOuterClass.PacketHead;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEnterWorldAreaRsp;

@Opcodes(PacketOpcodes.EnterWorldAreaReq)
public class HandlerEnterWorldAreaReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		PacketHead head = PacketHead.parseFrom(header);
		EnterWorldAreaReq enterWorld = EnterWorldAreaReq.parseFrom(payload);
		
		session.send(new PacketEnterWorldAreaRsp(head.getClientSequenceId(), enterWorld));
		//session.send(new PacketScenePlayerLocationNotify(session.getPlayer()));
	}

}
