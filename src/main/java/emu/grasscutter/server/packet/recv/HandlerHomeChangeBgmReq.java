package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeChangeBgmReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeChangeBgmNotify;
import emu.grasscutter.server.packet.send.PacketHomeChangeBgmRsp;

@Opcodes(PacketOpcodes.HomeChangeBgmReq)
public class HandlerHomeChangeBgmReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = HomeChangeBgmReqOuterClass.HomeChangeBgmReq.parseFrom(payload);

        int homeBgmId = req.getBgmId();
        var home = session.getPlayer().getHome();

        home.getHomeSceneItem(session.getPlayer().getSceneId()).setHomeBgmId(homeBgmId);
        home.save();

        session.send(new PacketHomeChangeBgmNotify(homeBgmId));
        session.send(new PacketHomeChangeBgmRsp());
    }
}
