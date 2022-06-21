package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.NpcTalkReqOuterClass.NpcTalkReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketNpcTalkRsp;

@Opcodes(PacketOpcodes.NpcTalkReq)
public class HandlerNpcTalkReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        NpcTalkReq req = NpcTalkReq.parseFrom(payload);

        // Why are there 2 quest triggers that do the same thing...
        session.getPlayer().getQuestManager().triggerEvent(QuestTrigger.QUEST_CONTENT_COMPLETE_TALK, req.getTalkId());
        session.getPlayer().getQuestManager().triggerEvent(QuestTrigger.QUEST_CONTENT_FINISH_PLOT, req.getTalkId());

        session.send(new PacketNpcTalkRsp(req.getNpcEntityId(), req.getTalkId(), req.getEntityId()));
    }

}
