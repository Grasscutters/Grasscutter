package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.world.*;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeChangeModuleReqOuterClass;
import emu.grasscutter.server.event.player.PlayerTeleportEvent.TeleportType;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.HomeChangeModuleReq)
public class HandlerHomeChangeModuleReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        HomeChangeModuleReqOuterClass.HomeChangeModuleReq req =
                HomeChangeModuleReqOuterClass.HomeChangeModuleReq.parseFrom(payload);
        session.getPlayer().setCurrentRealmId(req.getTargetModuleId());
        session.send(new PacketHomeChangeModuleRsp(req.getTargetModuleId()));
        session.send(new PacketPlayerHomeCompInfoNotify(session.getPlayer()));
        session.send(new PacketHomeComfortInfoNotify(session.getPlayer()));

        int realmId = 2000 + req.getTargetModuleId();

        Scene scene = session.getPlayer().getWorld().getSceneById(realmId);
        Position pos = scene.getScriptManager().getConfig().born_pos;

        session
                .getPlayer()
                .getWorld()
                .transferPlayerToScene(session.getPlayer(), realmId, TeleportType.WAYPOINT, pos);
    }
}
