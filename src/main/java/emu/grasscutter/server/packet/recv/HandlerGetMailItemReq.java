package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GetMailItemReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketGetMailItemRsp;

@Opcodes(PacketOpcodes.GetMailItemReq)
public class HandlerGetMailItemReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        GetMailItemReqOuterClass.GetMailItemReq req =
                GetMailItemReqOuterClass.GetMailItemReq.parseFrom(payload);
        session.send(new PacketGetMailItemRsp(session.getPlayer(), req.getMailIdListList()));
    }
}
