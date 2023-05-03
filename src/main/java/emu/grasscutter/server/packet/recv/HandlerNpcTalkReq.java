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
        var req = NpcTalkReq.parseFrom(payload);

        session.getPlayer().getTalkManager().triggerTalkAction(req.getTalkId());
        session.send(new PacketNpcTalkRsp(req.getNpcEntityId(), req.getTalkId(), req.getEntityId()));
    }
}
