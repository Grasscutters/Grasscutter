package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ReliquaryDecomposeReqOuterClass.ReliquaryDecomposeReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ReliquaryDecomposeReq)
public class HandlerReliquaryDecomposeReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ReliquaryDecomposeReq req = ReliquaryDecomposeReq.parseFrom(payload);
        session
                .getServer()
                .getCombineSystem()
                .decomposeReliquaries(
                        session.getPlayer(), req.getConfigId(), req.getTargetCount(), req.getGuidListList());
    }
}
