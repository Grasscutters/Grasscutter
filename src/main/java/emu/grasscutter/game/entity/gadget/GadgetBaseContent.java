package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import emu.grasscutter.net.proto.SelectWorktopOptionReqOuterClass;

public abstract class GadgetBaseContent {
	private final EntityGadget gadget;

	public GadgetBaseContent(EntityGadget gadget) {
		this.gadget = gadget;
	}

	public EntityGadget getGadget() {
		return gadget;
	}

	public abstract boolean onInteract(Player player, GadgetInteractReq req);

    public abstract boolean onSelectWorktopOption(Player player, SelectWorktopOptionReqOuterClass.SelectWorktopOptionReq req);

	public abstract void onBuildProto(SceneGadgetInfo.Builder gadgetInfo);
}
