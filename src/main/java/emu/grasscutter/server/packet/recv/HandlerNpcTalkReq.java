package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.data.binout.MainQuestData.TalkData;
import emu.grasscutter.game.quest.enums.QuestCond;
import emu.grasscutter.game.quest.enums.QuestContent;
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

        // Check if mainQuest exists
        // remove last 2 digits to get a mainQuestId
        int talkId = req.getTalkId();
        int mainQuestId = GameData.getQuestTalkMap().getOrDefault(talkId, talkId / 100);
        MainQuestData mainQuestData = GameData.getMainQuestDataMap().get(mainQuestId);

        if (mainQuestData != null) {
            // This talk is associated with a quest. Handle it.
            // If the quest has no talk data defined on it, create one.
            TalkData talkForQuest = new TalkData(talkId, "");
            if (mainQuestData.getTalks() != null) {
                var talks = mainQuestData.getTalks().stream().filter(p -> p.getId() == talkId).toList();

                if (talks.size() > 0) {
                    talkForQuest = talks.get(0);
                }
            }

            // Add to the list of done talks for this quest.
            var questManager = session.getPlayer().getQuestManager();
            var mainQuest = questManager.getMainQuestByTalkId(talkId);
            if (mainQuest != null) {
                mainQuest.getTalks().put(talkId, talkForQuest);
            }

            // Fire quest triggers.
            questManager.queueEvent(QuestContent.QUEST_CONTENT_COMPLETE_ANY_TALK, talkId, 0, 0);
            questManager.queueEvent(QuestContent.QUEST_CONTENT_COMPLETE_TALK, talkId, 0);
            questManager.queueEvent(QuestContent.QUEST_CONTENT_FINISH_PLOT, talkId, 0);
            questManager.queueEvent(QuestCond.QUEST_COND_COMPLETE_TALK, talkId, 0);
        }

        session.send(new PacketNpcTalkRsp(req.getNpcEntityId(), req.getTalkId(), req.getEntityId()));
    }
}
