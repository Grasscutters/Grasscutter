package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.ScenePointEntry;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.SceneTransToPointReqOuterClass.SceneTransToPointReq;
import emu.grasscutter.server.event.player.PlayerTeleportEvent.TeleportType;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketSceneTransToPointRsp;

@Opcodes(PacketOpcodes.SceneTransToPointReq)
public class HandlerSceneTransToPointReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        SceneTransToPointReq req = SceneTransToPointReq.parseFrom(payload);
        var player = session.getPlayer();

        ScenePointEntry scenePointEntry =
                GameData.getScenePointEntryById(req.getSceneId(), req.getPointId());

        if (scenePointEntry != null) {
            if (player
                    .getCurHomeWorld()
                    .isInHome(player)) { // if the player is in home, make the player go back
                session
                        .getServer()
                        .getHomeWorldMPSystem()
                        .leaveCoop(
                                player, req.getSceneId(), scenePointEntry.getPointData().getTranPos().clone());
                session.send(new PacketSceneTransToPointRsp(player, req.getPointId(), req.getSceneId()));
                return;
            } else if (player
                    .getWorld()
                    .transferPlayerToScene(
                            player,
                            req.getSceneId(),
                            TeleportType.WAYPOINT,
                            scenePointEntry.getPointData().getTranPos().clone())) {
                session.send(new PacketSceneTransToPointRsp(player, req.getPointId(), req.getSceneId()));
                return;
            }
        }

        session.send(new PacketSceneTransToPointRsp());
    }
}
