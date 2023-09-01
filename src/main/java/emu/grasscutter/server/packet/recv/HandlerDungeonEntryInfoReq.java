package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DungeonEntryInfoReqOuterClass.DungeonEntryInfoReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.DungeonEntryInfoReq)
public class HandlerDungeonEntryInfoReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        DungeonEntryInfoReq req = DungeonEntryInfoReq.parseFrom(payload);

        session
                .getServer()
                .getDungeonSystem()
                .sendEntryInfoFor(session.getPlayer(), req.getPointId(), req.getSceneId());
    }
}
