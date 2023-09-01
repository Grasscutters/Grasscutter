package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetCompoundDataReqOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.GetCompoundDataReq)
public class HandlerGetCompoundDataReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = GetCompoundDataReqOuterClass.GetCompoundDataReq.parseFrom(payload);
        session.getPlayer().getCookingCompoundManager().handleGetCompoundDataReq(req);
    }
}
