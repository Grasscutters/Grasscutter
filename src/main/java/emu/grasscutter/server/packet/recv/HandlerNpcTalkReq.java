package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.binout.MainQuestData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.game.quest.enums.ParentQuestState;
import emu.grasscutter.game.quest.enums.QuestTrigger;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.NpcTalkReqOuterClass.NpcTalkReq;
import emu.grasscutter.net.packet.PacketHandler;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketNpcTalkRsp;

@Opcodes(PacketOpcodes.NpcTalkReq)
public class HandlerNpcTalkReq extends PacketHandler {

	@Override
	public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
		NpcTalkReq req = NpcTalkReq.parseFrom(payload);
        //Check if mainQuest exists
        int talkId = req.getTalkId();
        //remove last 2 digits to get a mainQuestId
        int mainQuestId = talkId/100;
        MainQuestData mainQuestData = GameData.getMainQuestDataMap().get(mainQuestId);
        if(mainQuestData != null) {
            MainQuestData.TalkData talk = mainQuestData.getTalks().stream().filter(p -> p.getId() == talkId).toList().get(0);
            if(talk != null) {
                //talk is finished
                session.getPlayer().getQuestManager().getMainQuestById(mainQuestId).getTalks().put(Integer.valueOf(talkId),talk);
                session.getPlayer().getQuestManager().triggerEvent(QuestTrigger.QUEST_CONTENT_COMPLETE_ANY_TALK,String.valueOf(req.getTalkId()), 0, 0);
                // Why are there 2 quest triggers that do the same thing...
                session.getPlayer().getQuestManager().triggerEvent(QuestTrigger.QUEST_CONTENT_COMPLETE_TALK, req.getTalkId(),0);
                session.getPlayer().getQuestManager().triggerEvent(QuestTrigger.QUEST_CONTENT_FINISH_PLOT, req.getTalkId(),0);
                }
            }

		session.send(new PacketNpcTalkRsp(req.getNpcEntityId(), req.getTalkId(), req.getEntityId()));
	}

}