package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.ScenePointEntry;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.PersonalSceneJumpReqOuterClass.PersonalSceneJumpReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketPersonalSceneJumpRsp;

@Opcodes(PacketOpcodes.PersonalSceneJumpReq)
public class HandlerPersonalSceneJumpReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PersonalSceneJumpReq req = PersonalSceneJumpReq.parseFrom(payload);
        var player = session.getPlayer();

        // get the scene point
        ScenePointEntry scenePointEntry =
                GameData.getScenePointEntryById(player.getSceneId(), req.getPointId());

        if (scenePointEntry != null) {
            Position pos =
                    scenePointEntry.getPointData().getTranPos().clone(); // This might not need cloning
            int sceneId = scenePointEntry.getPointData().getTranSceneId();

            player.getWorld().transferPlayerToScene(player, sceneId, pos);
            session.send(new PacketPersonalSceneJumpRsp(sceneId, pos));
        }
    }
}
