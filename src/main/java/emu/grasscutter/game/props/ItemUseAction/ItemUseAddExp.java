package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;
import lombok.Getter;

public class ItemUseAddExp extends ItemUseAction {
    @Getter private int exp = 0;

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_EXP;
    }

    public ItemUseAddExp(String[] useParam) {
        try {
            this.exp = Integer.parseInt(useParam[0]);
        } catch (NumberFormatException ignored) {}
    }
}
