package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetShopReqOuterClass.GetShopReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetShopRsp;

@Opcodes(PacketOpcodes.GetShopReq)
public class HandlerGetShopReq extends PacketHandler {
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		GetShopReq req = GetShopReq.parseFrom(payload);
		
		// TODO
		session.send(new PacketGetShopRsp(req.getShopType()));
	}
}
