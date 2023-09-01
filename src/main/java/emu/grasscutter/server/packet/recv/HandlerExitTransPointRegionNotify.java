package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ExitTransPointRegionNotify)
public class HandlerExitTransPointRegionNotify extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session.getPlayer().getSotsManager().handleExitTransPointRegionNotify();
    }
}
