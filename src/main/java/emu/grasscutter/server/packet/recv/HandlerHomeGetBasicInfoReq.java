package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeBasicInfoNotify;

@Opcodes(PacketOpcodes.HomeGetBasicInfoReq)
public class HandlerHomeGetBasicInfoReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {

        session.send(new PacketHomeBasicInfoNotify(session.getPlayer(), false));
    }
}
