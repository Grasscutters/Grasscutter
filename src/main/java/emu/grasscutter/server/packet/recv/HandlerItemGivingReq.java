package emu.grasscutter.server.packet.recv;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.giving.GivingData.GiveMethod;
import emu.grasscutter.game.quest.enums.QuestContent;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.ItemGivingReqOuterClass.ItemGivingReq;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.send.PacketItemGivingRsp;
import emu.grasscutter.server.packet.send.PacketItemGivingRsp.Mode;

@Opcodes(PacketOpcodes.ItemGivingReq)
public final class HandlerItemGivingReq extends PacketHandler {
    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        var req = ItemGivingReq.parseFrom(payload);

        var player = session.getPlayer();
        var inventory = player.getInventory();

        var giveId = req.getGivingId();
        var items = req.getItemGuidCountMapMap();

        switch (req.getItemGivingType()) {
            case QUEST -> {
                var questManager = player.getQuestManager();
                var activeGivings = questManager.getActiveGivings();
                if (!activeGivings.contains(giveId)) return;

                // Check the items against the resources.
                var data = GameData.getGivingDataMap().get(giveId);
                if (data == null) throw new IllegalArgumentException("No giving data found for " + giveId + ".");

                if (data.getGivingMethod() == GiveMethod.EXACT) {
                    if (!inventory.hasAllItems(items)) {
                        player.sendPacket(new PacketItemGivingRsp());
                        return;
                    }

                    // Remove the items if the quest specifies.
                    if (data.isRemoveItem()) {
                        inventory.removeItems(items);
                    }

                    // Send the response packet.
                    player.sendPacket(new PacketItemGivingRsp(giveId, Mode.EXACT_SUCCESS));
                    // Remove the action from the active givings.
                    activeGivings.remove(giveId);
                    // Queue the content action.
                    questManager.queueEvent(QuestContent.QUEST_CONTENT_FINISH_ITEM_GIVING, giveId);
                } else {
                    // TODO: Handle group givings.
                    player.sendPacket(new PacketItemGivingRsp());
                }
            }
            case GADGET -> {
                Grasscutter.getLogger().debug("Unimplemented gadget giving was executed for {}.", giveId);
                player.sendPacket(new PacketItemGivingRsp());
            }
        }
    }
}
