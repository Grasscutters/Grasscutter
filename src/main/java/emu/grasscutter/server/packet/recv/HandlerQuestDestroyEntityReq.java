package emu.grasscutter.server.packet.recv;

import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.QuestDestroyEntityReqOuterClass.QuestDestroyEntityReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketQuestDestroyEntityRsp;
import lombok.val;

@Opcodes(PacketOpcodes.QuestDestroyEntityReq)
public class HandlerQuestDestroyEntityReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        val req = QuestDestroyEntityReq.parseFrom(payload);
        val scene = session.getPlayer().getWorld().getSceneById(req.getSceneId());
        val entity = scene.getEntityById(req.getEntityId());

        if (entity != null) {
            scene.removeEntity(entity);
        }

        session.send(new PacketQuestDestroyEntityRsp(entity != null, req));
    }
}
