package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeUpdateArrangementInfoReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.HomeUpdateArrangementInfoReq)
public class HandlerHomeUpdateArrangementInfoReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req =
                HomeUpdateArrangementInfoReqOuterClass.HomeUpdateArrangementInfoReq.parseFrom(payload);

        var homeScene =
                session.getPlayer().getHome().getHomeSceneItem(session.getPlayer().getSceneId());

        homeScene.update(req.getSceneArrangementInfo());

        session.send(new PacketHomeMarkPointNotify(session.getPlayer()));

        session.getPlayer().getHome().save();

        session.send(new PacketHomeUpdateArrangementInfoRsp());
    }
}
