package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetActivityShopSheetInfoReqOuterClass.GetActivityShopSheetInfoReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetActivityShopSheetInfoRsp;

@Opcodes(PacketOpcodes.GetActivityShopSheetInfoReq)
public class HandlerGetActivityShopSheetInfoReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        GetActivityShopSheetInfoReq req = GetActivityShopSheetInfoReq.parseFrom(payload);
        session.getPlayer().sendPacket(new PacketGetActivityShopSheetInfoRsp(req.getShopType()));
    }
}
