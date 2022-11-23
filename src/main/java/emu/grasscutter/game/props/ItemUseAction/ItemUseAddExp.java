package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseAddExp extends ItemUseInt {
    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_EXP;
    }

    public ItemUseAddExp(String[] useParam) {
        super(useParam);
    }

    public int getExp() {
        return this.i;
    }
}
