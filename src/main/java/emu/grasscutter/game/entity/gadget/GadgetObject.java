package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass;

public class GadgetObject extends GadgetContent{
    public GadgetObject(EntityGadget gadget) {
        super(gadget);
    }

    @Override
    public boolean onInteract(Player player, GadgetInteractReqOuterClass.GadgetInteractReq req) {
        return false;
    }

    @Override
    public void onBuildProto(SceneGadgetInfoOuterClass.SceneGadgetInfo.Builder gadgetInfo) {

    }
}
