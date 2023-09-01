package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetShopmallDataRsp;

@Opcodes(PacketOpcodes.GetShopmallDataReq)
public class HandlerGetShopmallDataReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        // TODO add the correct shops
        session.send(new PacketGetShopmallDataRsp());
    }
}
