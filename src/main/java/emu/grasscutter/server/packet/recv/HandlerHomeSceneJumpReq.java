package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeSceneJumpReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeSceneJumpRsp;

@Opcodes(PacketOpcodes.HomeSceneJumpReq)
public class HandlerHomeSceneJumpReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeSceneJumpReqOuterClass.HomeSceneJumpReq.parseFrom(payload);

        var world = session.getPlayer().getCurHomeWorld();
        var home = world.getHome();
        var owner = world.getHost();
        int realmId = 2000 + owner.getCurrentRealmId();
        var homeScene = home.getHomeSceneItem(realmId);
        home.save();

        var scene =
                world.getSceneById(req.getIsEnterRoomScene() ? homeScene.getRoomSceneId() : realmId);
        var pos = scene.getScriptManager().getConfig().born_pos;
        var rot = home.getHomeSceneItem(scene.getId()).getBornRot();

        // Make player face correct direction when entering or exiting
        session.getPlayer().getRotation().set(rot);

        // Make player exit to front of main house
        if (!req.getIsEnterRoomScene()) {
            pos = home.getSceneMap().get(realmId).getBornPos();
        }

        world.transferPlayerToScene(
                session.getPlayer(), req.getIsEnterRoomScene() ? homeScene.getRoomSceneId() : realmId, pos);

        session.send(new PacketHomeSceneJumpRsp(req.getIsEnterRoomScene()));
    }
}
