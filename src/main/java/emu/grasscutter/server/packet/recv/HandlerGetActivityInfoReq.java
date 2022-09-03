package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.proto.GetActivityInfoReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetActivityInfoRsp;

import java.util.HashSet;

@Opcodes(PacketOpcodes.GetActivityInfoReq)
public class HandlerGetActivityInfoReq extends PacketHandler {
	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = GetActivityInfoReqOuterClass.GetActivityInfoReq.parseFrom(payload);

		session.send(new PacketGetActivityInfoRsp(
            new HashSet<>(req.getActivityIdListList()),
            session.getPlayer().getActivityManager()));
	}
}
