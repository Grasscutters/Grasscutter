package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.HomeChooseModuleReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.HomeChooseModuleReq)
public class HandlerHomeChooseModuleReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        HomeChooseModuleReqOuterClass.HomeChooseModuleReq req =
                HomeChooseModuleReqOuterClass.HomeChooseModuleReq.parseFrom(payload);
        session.getPlayer().addRealmList(req.getModuleId());
        session.getPlayer().setCurrentRealmId(req.getModuleId());
        session.getPlayer().getCurHomeWorld().refreshModuleManager();
        session.send(new PacketHomeChooseModuleRsp(req.getModuleId()));
        session.send(new PacketPlayerHomeCompInfoNotify(session.getPlayer()));
        session.send(new PacketHomeComfortInfoNotify(session.getPlayer()));
    }
}
