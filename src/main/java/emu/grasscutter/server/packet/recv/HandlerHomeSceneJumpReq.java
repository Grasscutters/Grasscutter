package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeSceneJumpReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeSceneJumpRsp;
import emu.grasscutter.utils.Position;

@Opcodes(PacketOpcodes.HomeSceneJumpReq)
public class HandlerHomeSceneJumpReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeSceneJumpReqOuterClass.HomeSceneJumpReq.parseFrom(payload);

        int realmId = 2000 + session.getPlayer().getCurrentRealmId();

        var home = session.getPlayer().getHome();
        var homeScene = home.getHomeSceneItem(realmId);
        home.save();

        Scene scene = session.getPlayer().getWorld().getSceneById(req.getIsEnterRoomScene() ? homeScene.getRoomSceneId() : realmId);
        Position pos = scene.getScriptManager().getConfig().born_pos;

        session.getPlayer().getWorld().transferPlayerToScene(
            session.getPlayer(),
            req.getIsEnterRoomScene() ? homeScene.getRoomSceneId() : realmId,
            pos
        );

        session.send(new PacketHomeSceneJumpRsp(req.getIsEnterRoomScene()));
    }

}
