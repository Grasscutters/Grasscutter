package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseGainFlycloak extends ItemUseInt {
    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_GAIN_FLYCLOAK;
    }

    public ItemUseGainFlycloak(String[] useParam) {
        super(useParam);
    }

    @Override
    public boolean useItem(UseItemParams params) {
        params.player.getInventory().addItem(this.i);  // TODO: Currently this returns false for all virtual items - need to have a proper success/fail
        return true;
    }
}
