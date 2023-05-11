package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseGainNameCard extends ItemUseAction {
    public ItemUseGainNameCard(String[] useParam) {}

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_GAIN_NAME_CARD;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        params.player.addNameCard(params.usedItemId);
        return true;
    }
}
