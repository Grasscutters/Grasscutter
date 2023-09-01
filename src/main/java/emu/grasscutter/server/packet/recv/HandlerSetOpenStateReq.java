package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SetOpenStateReqOuterClass.SetOpenStateReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.SetOpenStateReq)
public class HandlerSetOpenStateReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = SetOpenStateReq.parseFrom(payload);
        int openState = req.getKey();
        int value = req.getValue();

        session.getPlayer().getProgressManager().setOpenStateFromClient(openState, value);
    }
}
