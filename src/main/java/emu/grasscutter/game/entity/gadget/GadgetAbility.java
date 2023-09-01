package emu.grasscutter.game.entity.gadget;

import emu.grasscutter.game.entity.*;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.proto.AbilityGadgetInfoOuterClass;
import emu.grasscutter.net.proto.GadgetInteractReqOuterClass.GadgetInteractReq;
import emu.grasscutter.net.proto.SceneGadgetInfoOuterClass.SceneGadgetInfo;
import lombok.val;

public class GadgetAbility extends GadgetContent {
    private EntityBaseGadget parent;

    public GadgetAbility(EntityGadget gadget, EntityBaseGadget parent) {
        super(gadget);

        this.parent = parent;
    }

    public boolean onInteract(Player player, GadgetInteractReq req) {
        return false;
    }

    public void onBuildProto(SceneGadgetInfo.Builder gadgetInfo) {
        if (this.parent == null) {
            return;
        }

        val abilityGadgetInfo =
                AbilityGadgetInfoOuterClass.AbilityGadgetInfo.newBuilder()
                        .setCampId(parent.getCampId())
                        .setCampTargetType(parent.getCampType())
                        .setTargetEntityId(parent.getId())
                        .build();

        gadgetInfo.setAbilityGadget(abilityGadgetInfo);
    }
}
