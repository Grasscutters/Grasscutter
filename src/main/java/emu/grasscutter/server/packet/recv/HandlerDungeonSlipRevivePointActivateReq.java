package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.DungeonSlipRevivePointActivateReqOuterClass.DungeonSlipRevivePointActivateReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketDungeonSlipRevivePointActivateRsp;

@Opcodes(PacketOpcodes.DungeonSlipRevivePointActivateReq)
public class HandlerDungeonSlipRevivePointActivateReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = DungeonSlipRevivePointActivateReq.parseFrom(payload);
        var dungeonManager = session.getPlayer().getScene().getDungeonManager();

        boolean success = false;
        if (dungeonManager != null) {
            success = dungeonManager.activateRespawnPoint(req.getSlipRevivePointId());
        }

        session.send(new PacketDungeonSlipRevivePointActivateRsp(success, req.getSlipRevivePointId()));
    }
}
