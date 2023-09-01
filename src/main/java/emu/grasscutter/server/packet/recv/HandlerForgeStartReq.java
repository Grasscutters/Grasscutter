package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ForgeStartReqOuterClass;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.ForgeStartReq)
public class HandlerForgeStartReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        ForgeStartReqOuterClass.ForgeStartReq req =
                ForgeStartReqOuterClass.ForgeStartReq.parseFrom(payload);
        session.getPlayer().getForgingManager().handleForgeStartReq(req);
    }
}
