package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PlayerCompoundMaterialReqOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.PlayerCompoundMaterialReq)
public class HandlerPlayerCompoundMaterialReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = PlayerCompoundMaterialReqOuterClass.PlayerCompoundMaterialReq.parseFrom(payload);
        session.getPlayer().getCookingCompoundManager().handlePlayerCompoundMaterialReq(req);
    }
}
