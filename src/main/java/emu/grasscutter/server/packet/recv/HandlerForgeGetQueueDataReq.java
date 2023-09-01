package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ForgeGetQueueDataReq)
public class HandlerForgeGetQueueDataReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        session.getPlayer().getForgingManager().handleForgeGetQueueDataReq();
    }
}
