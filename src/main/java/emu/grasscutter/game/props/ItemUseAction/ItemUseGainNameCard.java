package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseGainNameCard extends ItemUseAction {
    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_GAIN_NAME_CARD;
    }

    public ItemUseGainNameCard(String[] useParam) {
    }

    @Override
    public boolean useItem(UseItemParams params) {
        return false;  // TODO: work out if this is actually used and how to get the namecard id
    }
}
