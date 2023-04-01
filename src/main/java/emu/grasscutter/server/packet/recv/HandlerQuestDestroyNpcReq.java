package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.entity.EntityNPC;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketQuestDestroyNpcRsp;
import lombok.val;
import emu.grasscutter.net.proto.QuestDestroyNpcReqOuterClass.QuestDestroyNpcReq;

@Opcodes(PacketOpcodes.QuestDestroyNpcReq)
public class HandlerQuestDestroyNpcReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        val req = QuestDestroyNpcReq.parseFrom(payload);

        session.send(new PacketQuestDestroyNpcRsp(req.getNpcId(), req.getParentQuestId(), 0));
    }
}
