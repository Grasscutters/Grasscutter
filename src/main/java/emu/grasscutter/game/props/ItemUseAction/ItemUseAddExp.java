package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseAddExp extends ItemUseInt {
    public ItemUseAddExp(String[] useParam) {
        super(useParam);
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_EXP;
    }

    public int getExp() {
        return this.i;
    }
}
