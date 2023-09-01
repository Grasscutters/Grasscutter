package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeModuleSeenReqOuterClass.HomeModuleSeenReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.HomeModuleSeenReq)
public class HandlerHomeModuleSeenReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeModuleSeenReq.parseFrom(payload);
        var seen = req.getSeenModuleIdListList();

        // As multiple may be seen at once, add each
        for (int i : seen) {
            session.getPlayer().addSeenRealmList(i);
        }

        session.send(new PacketPlayerHomeCompInfoNotify(session.getPlayer()));
        session.send(new PacketHomeModuleSeenRsp(seen));
    }
}
