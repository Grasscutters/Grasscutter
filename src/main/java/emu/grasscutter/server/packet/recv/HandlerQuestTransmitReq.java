package emu.grasscutter.server.packet.recv;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.world.Position;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.QuestTransmitReqOuterClass.QuestTransmitReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketQuestTransmitRsp;
import java.util.ArrayList;

@Opcodes(PacketOpcodes.QuestTransmitReq)
public class HandlerQuestTransmitReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = QuestTransmitReq.parseFrom(payload);
        var mainQuest = session.getPlayer().getQuestManager().getMainQuestById(req.getQuestId() / 100);

        var posAndRot = new ArrayList<Position>();
        boolean result = false;
        if (mainQuest.hasTeleportPosition(req.getQuestId(), posAndRot)) {
            var sceneId =
                    GameData.getTeleportDataMap()
                            .get(req.getQuestId())
                            .getTransmit_points()
                            .get(0)
                            .getScene_id();
            result =
                    session
                            .getPlayer()
                            .getWorld()
                            .transferPlayerToScene(session.getPlayer(), sceneId, posAndRot.get(0));
        }

        session.send(new PacketQuestTransmitRsp(result, req));
    }
}
