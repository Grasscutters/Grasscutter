package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeChangeBgmReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.HomeChangeBgmReq)
public class HandlerHomeChangeBgmReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeChangeBgmReqOuterClass.HomeChangeBgmReq.parseFrom(payload);

        int homeBgmId = req.getBgmId();
        var home = session.getPlayer().getHome();

        home.getHomeSceneItem(session.getPlayer().getSceneId()).setHomeBgmId(homeBgmId);
        home.save();

        session.getPlayer().getScene().broadcastPacket(new PacketHomeChangeBgmNotify(homeBgmId));
        session.send(new PacketHomeChangeBgmRsp());
    }
}
