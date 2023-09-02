package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerApplyEnterHomeResultReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPlayerApplyEnterHomeResultRsp;

@Opcodes(PacketOpcodes.PlayerApplyEnterHomeResultReq)
public class HandlerPlayerApplyEnterHomeResultReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req =
                PlayerApplyEnterHomeResultReqOuterClass.PlayerApplyEnterHomeResultReq.parseFrom(payload);

        session
                .getServer()
                .getHomeWorldMPSystem()
                .acceptEnterHomeRequest(session.getPlayer(), req.getApplyUid(), req.getIsAgreed());
        session.send(new PacketPlayerApplyEnterHomeResultRsp(req.getApplyUid(), req.getIsAgreed()));
    }
}
