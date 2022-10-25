package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;
import lombok.Getter;

public class ItemUseAddReliquaryExp extends ItemUseAction {
    @Getter private int exp = 0;

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_RELIQUARY_EXP;
    }

    public ItemUseAddReliquaryExp(String[] useParam) {
        try {
            this.exp = Integer.parseInt(useParam[0]);
        } catch (NumberFormatException ignored) {}
    }
}
