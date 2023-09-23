package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.quest.enums.QuestCond;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.StartCoopPointReqOuterClass;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketStartCoopPointRsp;

@Opcodes(PacketOpcodes.StartCoopPointReq)
public class HandlerStartCoopPointReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        StartCoopPointReqOuterClass.StartCoopPointReq req =
                StartCoopPointReqOuterClass.StartCoopPointReq.parseFrom(payload);
        var coopPoint = req.getCoopPoint();

        var coopPointData =
                GameData.getCoopPointDataMap().values().stream()
                        .filter(i -> i.getId() == coopPoint)
                        .toList();
        if (!coopPointData.isEmpty()) {
            var player = session.getPlayer();
            var questManager = player.getQuestManager();
            questManager.queueEvent(
                    QuestCond.QUEST_COND_MAIN_COOP_START, coopPointData.get(0).getChapterId(), 0);
        }
        session.send(new PacketStartCoopPointRsp(coopPoint));
    }
}
