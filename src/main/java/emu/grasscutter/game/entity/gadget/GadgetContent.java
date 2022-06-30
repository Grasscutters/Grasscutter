package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.SelectWorktopOptionReqOuterClass;

public class GadgetContent {
	private final EntityGadget gadget;

	public GadgetContent(EntityGadget gadget) {
		this.gadget = gadget;
	}

	public EntityGadget getGadget() {
		return gadget;
	}

	public boolean onInteract(Player player, GadgetInteractReq req) {
	    return false;
    }

    public boolean onSelectWorktopOption(Player player, SelectWorktopOptionReqOuterClass.SelectWorktopOptionReq req) {
        return false;
    }

	public void onBuildProto(SceneGadgetInfo.Builder gadgetInfo) {
    }
}
