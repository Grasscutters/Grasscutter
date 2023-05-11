package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.GatherData;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.GatherGadgetInfoOuterClass.GatherGadgetInfo;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;

/** Spawner for the gather objects */
public final class GadgetGatherPoint extends GadgetContent {
    private final GatherData gatherData;
    private final EntityGadget gatherObjectChild;

    public GadgetGatherPoint(EntityGadget gadget) {
        super(gadget);

        this.gatherData = GameData.getGatherDataMap().get(gadget.getPointType());

        var scene = gadget.getScene();
        gatherObjectChild = new EntityGadget(scene, gatherData.getGadgetId(), gadget.getBornPos());

        gatherObjectChild.setBlockId(gadget.getBlockId());
        gatherObjectChild.setConfigId(gadget.getConfigId());
        gatherObjectChild.setGroupId(gadget.getGroupId());
        gatherObjectChild.getRotation().set(gadget.getRotation());
        gatherObjectChild.setState(gadget.getState());
        gatherObjectChild.setPointType(gadget.getPointType());
        gatherObjectChild.setMetaGadget(gadget.getMetaGadget());
        gatherObjectChild.buildContent();

        gadget.getChildren().add(gatherObjectChild);
        scene.addEntity(gatherObjectChild);
    }

    public int getItemId() {
        return this.gatherData.getItemId();
    }

    public boolean isForbidGuest() {
        return this.gatherData.isForbidGuest();
    }

    public boolean onInteract(Player player, GadgetInteractReq req) {
        GameItem item = new GameItem(getItemId(), 1);

        player.getInventory().addItem(item, ActionReason.Gather);

        return true;
    }

    public void onBuildProto(SceneGadgetInfo.Builder gadgetInfo) {
        // todo does official use this for the spawners?
        GatherGadgetInfo gatherGadgetInfo =
                GatherGadgetInfo.newBuilder()
                        .setItemId(this.getItemId())
                        .setIsForbidGuest(this.isForbidGuest())
                        .build();

        gadgetInfo.setGatherGadget(gatherGadgetInfo);
    }
}
