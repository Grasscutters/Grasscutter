package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.*;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.*;
import emu.grasscutter.scripts.constants.EventType;
import emu.grasscutter.scripts.data.ScriptArgs;
import emu.grasscutter.server.packet.send.PacketGadgetInteractRsp;

public class GadgetObject extends GadgetContent {
    private int itemId;

    public GadgetObject(EntityGadget gadget) {
        super(gadget);
        GatherData gatherData = GameData.getGatherDataMap().get(gadget.getPointType());
        if (gatherData != null) {
            this.itemId = gatherData.getItemId();
        }
    }

    @Override
    public boolean onInteract(Player player, GadgetInteractReqOuterClass.GadgetInteractReq req) {
        // This is a workaround until a proper gadget interaction system can be put in place.
        ItemData itemData = GameData.getItemDataMap().get(this.itemId);
        if (itemData == null) {
            return false;
        }

        GameItem item = new GameItem(itemData, 1);
        player.getInventory().addItem(item, ActionReason.Gather);

        var ScriptArgs =
                new ScriptArgs(getGadget().getGroupId(), EventType.EVENT_GATHER, getGadget().getConfigId());
        if (getGadget().getMetaGadget() != null) {
            ScriptArgs.setEventSource(getGadget().getMetaGadget().config_id);
        }
        getGadget().getScene().getScriptManager().callEvent(ScriptArgs);

        getGadget()
                .getScene()
                .broadcastPacket(
                        new PacketGadgetInteractRsp(
                                getGadget(), InteractTypeOuterClass.InteractType.INTERACT_TYPE_GATHER));
        return true;
    }

    @Override
    public void onBuildProto(SceneGadgetInfoOuterClass.SceneGadgetInfo.Builder gadgetInfo) {}
}
