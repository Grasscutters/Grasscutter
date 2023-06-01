package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseGainCardProduct extends ItemUseAction {
    public ItemUseGainCardProduct(String[] useParam) {}

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_GAIN_CARD_PRODUCT;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        return params.player.rechargeMoonCard();
    }
}
