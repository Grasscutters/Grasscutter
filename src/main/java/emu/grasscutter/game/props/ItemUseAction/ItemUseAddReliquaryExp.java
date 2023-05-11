package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseAddReliquaryExp extends ItemUseAddExp {
    public ItemUseAddReliquaryExp(String[] useParam) {
        super(useParam);
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_RELIQUARY_EXP;
    }
}
