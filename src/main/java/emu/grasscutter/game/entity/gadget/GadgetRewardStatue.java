package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.game.dungeons.challenge.DungeonChallenge;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.InteractTypeOuterClass.InteractType;
import emu.grasscutter.net.proto.ResinCostTypeOuterClass;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.server.packet.send.PacketGadgetInteractRsp;

public final class GadgetRewardStatue extends GadgetContent {

    public GadgetRewardStatue(EntityGadget gadget) {
        super(gadget);
    }

    public boolean onInteract(Player player, GadgetInteractReq req) {
        var dungeonManager = player.getScene().getDungeonManager();

        if (player.getScene().getChallenge() instanceof DungeonChallenge) {
            var useCondensed =
                    req.getResinCostType() == ResinCostTypeOuterClass.ResinCostType.RESIN_COST_TYPE_CONDENSE;
            dungeonManager.getStatueDrops(player, useCondensed, getGadget().getGroupId());
        }

        player.sendPacket(
                new PacketGadgetInteractRsp(getGadget(), InteractType.INTERACT_TYPE_OPEN_STATUE));

        return false;
    }

    public void onBuildProto(SceneGadgetInfo.Builder gadgetInfo) {}
}
