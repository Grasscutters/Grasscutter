package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ForgeQueueManipulateReqOuterClass.ForgeQueueManipulateReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ForgeQueueManipulateReq)
public class HandlerForgeQueueManipulateReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ForgeQueueManipulateReq req = ForgeQueueManipulateReq.parseFrom(payload);
        session.getPlayer().getForgingManager().handleForgeQueueManipulateReq(req);
    }
}
