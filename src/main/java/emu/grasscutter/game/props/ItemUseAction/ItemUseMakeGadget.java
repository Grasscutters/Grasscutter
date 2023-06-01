package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.entity.EntityVehicle;
import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseMakeGadget extends ItemUseInt {
    public ItemUseMakeGadget(String[] useParam) {
        super(useParam);
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_MAKE_GADGET;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        var player = params.player;
        var scene = player.getScene();
        var pos = player.getPosition().nearby2d(1f);
        var rot = player.getRotation().clone();
        var e = new EntityVehicle(scene, player, this.i, 0, pos, rot);
        scene.addEntity(e);
        return true;
    }
}
