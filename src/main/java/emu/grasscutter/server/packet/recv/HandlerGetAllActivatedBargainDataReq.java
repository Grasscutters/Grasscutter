package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetAllActivatedBargainDataRsp;

@Opcodes(PacketOpcodes.GetAllActivatedBargainDataReq)
public final class HandlerGetAllActivatedBargainDataReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) {
        session.send(
                new PacketGetAllActivatedBargainDataRsp(
                        session.getPlayer().getPlayerProgress().getBargains().values()));
    }
}
