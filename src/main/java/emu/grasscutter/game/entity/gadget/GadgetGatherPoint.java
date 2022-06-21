package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.GatherData;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.net.proto.GatherGadgetInfoOuterClass.GatherGadgetInfo;
import emu.grasscutter.net.proto.InterOpTypeOuterClass;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;

public class GadgetGatherPoint extends GadgetContent {
    private final GatherData gatherData;

    
    public GadgetGatherPoint(EntityGadget gadget) {
        super(gadget);
        this.gatherData = GameData.getGatherDataMap().get(gadget.getPointType());
    }

    public GatherData getGatherData() {
        return this.gatherData;
    }

    public int getItemId() {
        return this.getGatherData().getItemId();
    }

    public boolean onInteract(Player player, GadgetInteractReq req) {
        GameItem item = new GameItem(gatherData.getItemId(), 1);
        
        player.getInventory().addItem(item, ActionReason.Gather);
        
        return true;
    }

    public void onBuildProto(SceneGadgetInfo.Builder gadgetInfo) {
        GatherGadgetInfo gatherGadgetInfo = GatherGadgetInfo.newBuilder()
            .setItemId(this.getItemId())
            .setIsForbidGuest(this.getGatherData().isForbidGuest())
            .build();

        gadgetInfo.setGatherGadget(gatherGadgetInfo);
    }
}
