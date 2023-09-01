package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.GetPlayerBlacklistReq)
public class HandlerGetPlayerBlacklistReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session.send(new BasePacket(PacketOpcodes.GetPlayerBlacklistRsp).buildHeader(3));
    }
}
