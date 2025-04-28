package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import lombok.Getter;

@Getter
public abstract class GadgetContent {
    private final EntityGadget gadget;

    public GadgetContent(EntityGadget gadget) {
        this.gadget = gadget;
    }

    public abstract boolean onInteract(Player player, GadgetInteractReq req);

    public abstract void onBuildProto(SceneGadgetInfo.Builder gadgetInfo);
}
