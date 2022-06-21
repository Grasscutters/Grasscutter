package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.TryEnterHomeReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketTryEnterHomeRsp;
import emu.grasscutter.utils.Position;

@Opcodes(PacketOpcodes.TryEnterHomeReq)
public class HandlerTryEnterHomeReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        TryEnterHomeReqOuterClass.TryEnterHomeReq req =
            TryEnterHomeReqOuterClass.TryEnterHomeReq.parseFrom(payload);

        if (req.getTargetUid() != session.getPlayer().getUid()) {
            // I hope that tomorrow there will be a hero who can support multiplayer mode and write code like a poem
            session.send(new PacketTryEnterHomeRsp());
            return;
        }

        int realmId = 2000 + session.getPlayer().getCurrentRealmId();

        var home = session.getPlayer().getHome();

        // prepare the default arrangement for first come in
        var homeScene = home.getHomeSceneItem(realmId);
        home.save();

        Scene scene = session.getPlayer().getWorld().getSceneById(realmId);
        Position pos = scene.getScriptManager().getConfig().born_pos;

        session.getPlayer().getWorld().transferPlayerToScene(
            session.getPlayer(),
            realmId,
            pos
        );


        session.send(new PacketTryEnterHomeRsp(req.getTargetUid()));
    }
}
