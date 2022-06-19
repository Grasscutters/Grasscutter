package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.HomeChooseModuleReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketHomeChooseModuleRsp;
import emu.grasscutter.server.packet.send.PacketHomeComfortInfoNotify;
import emu.grasscutter.server.packet.send.PacketPlayerHomeCompInfoNotify;


@Opcodes(PacketOpcodes.HomeChooseModuleReq)
public class HandlerHomeChooseModuleReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        HomeChooseModuleReqOuterClass.HomeChooseModuleReq req =
            HomeChooseModuleReqOuterClass.HomeChooseModuleReq.parseFrom(payload);
        session.getPlayer().addRealmList(req.getModuleId());
        session.getPlayer().setCurrentRealmId(req.getModuleId());
        session.send(new PacketHomeChooseModuleRsp(req.getModuleId()));
        session.send(new PacketPlayerHomeCompInfoNotify(session.getPlayer()));
        session.send(new PacketHomeComfortInfoNotify(session.getPlayer()));
    }
}
