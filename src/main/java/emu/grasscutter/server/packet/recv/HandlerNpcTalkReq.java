package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.NpcTalkReqOuterClass.NpcTalkReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketNpcTalkRsp;

@Opcodes(PacketOpcodes.NpcTalkReq)
public class HandlerNpcTalkReq extends PacketHandler {
	
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		NpcTalkReq req = NpcTalkReq.parseFrom(payload);

		session.send(new PacketNpcTalkRsp(req.getNpcEntityId(), req.getTalkId(), req.getEntityId()));
	}

}
