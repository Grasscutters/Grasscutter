package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.MarkMapReqOuterClass.MarkMapReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.MarkMapReq)
public class HandlerMarkMapReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        MarkMapReq req = MarkMapReq.parseFrom(payload);
        session.getPlayer().getMapMarksManager().handleMapMarkReq(req);
    }
}
