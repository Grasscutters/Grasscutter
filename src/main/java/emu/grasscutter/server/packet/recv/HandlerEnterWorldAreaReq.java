package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.EnterWorldAreaReqOuterClass.EnterWorldAreaReq;
import emu.grasscutter.net.proto.PacketHeadOuterClass.PacketHead;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketEnterWorldAreaRsp;

@Opcodes(PacketOpcodes.EnterWorldAreaReq)
public class HandlerEnterWorldAreaReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        PacketHead head = PacketHead.parseFrom(header);
        EnterWorldAreaReq enterWorld = EnterWorldAreaReq.parseFrom(payload);

        session.getPlayer().setArea(enterWorld.getAreaId(), enterWorld.getAreaType());
        session.send(new PacketEnterWorldAreaRsp(head.getClientSequenceId(), enterWorld));
        // session.send(new PacketScenePlayerLocationNotify(session.getPlayer()));
    }
}
