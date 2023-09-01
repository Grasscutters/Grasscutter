package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetPlayerSignatureReqOuterClass.SetPlayerSignatureReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSetPlayerSignatureRsp;

@Opcodes(PacketOpcodes.SetPlayerSignatureReq)
public class HandlerSetPlayerSignatureReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        // Auto template
        SetPlayerSignatureReq req = SetPlayerSignatureReq.parseFrom(payload);

        if (req.getSignature() != null && req.getSignature().length() > 0) {
            session.getPlayer().setSignature(req.getSignature());
            session.send(new PacketSetPlayerSignatureRsp(session.getPlayer()));
        }
    }
}
