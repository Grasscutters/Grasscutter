package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;

public abstract class GadgetContent {
	private EntityGadget gadget;

	public GadgetContent() {
	}

	public void setGadget(EntityGadget gadget) {
		this.gadget=gadget;
	}

	public EntityGadget getGadget() {
		return gadget;
	}
	
	public abstract boolean onInteract(Player player, GadgetInteractReq req);
	
	public abstract void onBuildProto(SceneGadgetInfo.Builder gadgetInfo);
}
