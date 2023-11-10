package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeChangeModuleReqOuterClass.HomeChangeModuleReq;
import emu.grasscutter.net.proto.RetcodeOuterClass.Retcode;
import emu.grasscutter.server.event.player.PlayerTeleportEvent.TeleportType;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeAvatarTalkFinishInfoNotify;
import emu.grasscutter.server.packet.send.PacketHomeChangeModuleRsp;
import emu.grasscutter.server.packet.send.PacketHomeComfortInfoNotify;
import emu.grasscutter.server.packet.send.PacketPlayerHomeCompInfoNotify;

@Opcodes(PacketOpcodes.HomeChangeModuleReq)
public class HandlerHomeChangeModuleReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeChangeModuleReq.parseFrom(payload);

        var homeWorld = session.getPlayer().getCurHomeWorld();
        if (!homeWorld.getGuests().isEmpty()) {
            session.send(new PacketHomeChangeModuleRsp(Retcode.RET_HOME_HAS_GUEST));
            return;
        }

        int realmId = 2000 + req.getTargetModuleId();
        var scene = homeWorld.getSceneById(realmId);

        if (scene == null) {
            Grasscutter.getLogger().warn("scene == null! Changing module will fail.");
            session.send(new PacketHomeChangeModuleRsp(Retcode.RET_INVALID_SCENE_ID));
            return;
        }

        session.getPlayer().setCurrentRealmId(req.getTargetModuleId());
        session.send(new PacketHomeAvatarTalkFinishInfoNotify(session.getPlayer()));
        session.send(new PacketHomeChangeModuleRsp(req.getTargetModuleId()));
        session.send(new PacketPlayerHomeCompInfoNotify(session.getPlayer()));
        session.send(new PacketHomeComfortInfoNotify(session.getPlayer()));

        var pos = scene.getScriptManager().getConfig().born_pos;

        homeWorld.transferPlayerToScene(session.getPlayer(), realmId, TeleportType.WAYPOINT, pos);
        homeWorld.refreshModuleManager();
    }
}
