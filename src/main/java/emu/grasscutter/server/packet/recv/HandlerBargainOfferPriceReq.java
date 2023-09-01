package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.BargainOfferPriceReqOuterClass.BargainOfferPriceReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketBargainOfferPriceRsp;

@Opcodes(PacketOpcodes.BargainOfferPriceReq)
public final class HandlerBargainOfferPriceReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var packet = BargainOfferPriceReq.parseFrom(payload);
        var player = session.getPlayer();

        // Fetch the active bargain.
        var bargainId = packet.getBargainId();
        var progress = player.getPlayerProgress();
        var bargain = progress.getBargains().get(bargainId);
        if (bargain == null) return;

        // Apply the offer.
        var result = bargain.applyOffer(packet.getPrice());

        // Queue the quest content event.
        var questManager = player.getQuestManager();
        switch (result) {
            case BARGAIN_COMPLETE_SUCC -> questManager.queueEvent(
                    QuestContent.QUEST_CONTENT_BARGAIN_SUCC, bargainId, 0);
            case BARGAIN_SINGLE_FAIL -> questManager.queueEvent(
                    QuestContent.QUEST_CONTENT_ITEM_LESS_THAN_BARGAIN, bargainId, 0);
            case BARGAIN_COMPLETE_FAIL -> questManager.queueEvent(
                    QuestContent.QUEST_CONTENT_BARGAIN_FAIL, bargainId, 0);
        }

        // Return the resulting packet.
        session.send(new PacketBargainOfferPriceRsp(result, bargain));
    }
}
