package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.server.game.GameSession;

@Opcodes(PacketOpcodes.GadgetInteractReq)
public class HandlerGadgetInteractReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        GadgetInteractReq req = GadgetInteractReq.parseFrom(payload);

        session
                .getPlayer()
                .getQuestManager()
                .queueEvent(QuestContent.QUEST_CONTENT_INTERACT_GADGET, req.getGadgetId());
        session.getPlayer().interactWith(req.getGadgetEntityId(), req);
    }
}
