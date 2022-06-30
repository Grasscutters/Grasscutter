package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass;
import emu.grasscutter.net.proto.SelectWorktopOptionReqOuterClass;

public class GadgetContent extends GadgetBaseContent {
    public GadgetContent(EntityGadget gadget) {
        super(gadget);
    }

    @Override
    public boolean onInteract(Player player, GadgetInteractReqOuterClass.GadgetInteractReq req) {
        return false;
    }

    @Override
    public boolean onSelectWorktopOption(Player player, SelectWorktopOptionReqOuterClass.SelectWorktopOptionReq req) {
        return false;
    }

    @Override
    public void onBuildProto(SceneGadgetInfoOuterClass.SceneGadgetInfo.Builder gadgetInfo) {

    }
}
