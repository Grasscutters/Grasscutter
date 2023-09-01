package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.NpcTalkReqOuterClass.NpcTalkReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketNpcTalkRsp;

@Opcodes(PacketOpcodes.NpcTalkReq)
public class HandlerNpcTalkReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = NpcTalkReq.parseFrom(payload);

        session.getPlayer().getTalkManager().triggerTalkAction(req.getTalkId(), req.getEntityId());
        session.send(new PacketNpcTalkRsp(req.getNpcEntityId(), req.getTalkId(), req.getEntityId()));
    }
}
