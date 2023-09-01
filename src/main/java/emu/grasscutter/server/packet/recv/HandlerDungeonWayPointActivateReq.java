package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DungeonWayPointActivateReqOuterClass.DungeonWayPointActivateReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketDungeonWayPointActivateRsp;

@Opcodes(PacketOpcodes.DungeonWayPointActivateReq)
public class HandlerDungeonWayPointActivateReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = DungeonWayPointActivateReq.parseFrom(payload);
        var dungeonManager = session.getPlayer().getScene().getDungeonManager();

        boolean success = false;
        if (dungeonManager != null) {
            success = dungeonManager.activateRespawnPoint(req.getWayPointId());
        }

        session.send(new PacketDungeonWayPointActivateRsp(success, req.getWayPointId()));
    }
}
