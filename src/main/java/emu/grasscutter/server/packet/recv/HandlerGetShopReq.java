package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetShopReqOuterClass.GetShopReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetShopRsp;

@Opcodes(PacketOpcodes.GetShopReq)
public class HandlerGetShopReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        GetShopReq req = GetShopReq.parseFrom(payload);

        session.send(new PacketGetShopRsp(session.getPlayer(), req.getShopType()));
    }
}
