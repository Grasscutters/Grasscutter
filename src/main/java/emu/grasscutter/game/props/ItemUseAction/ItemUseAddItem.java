package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.*;

public class ItemUseAddItem extends ItemUseInt {
    private int count = 0;

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_ITEM;
    }

    public ItemUseAddItem(String[] useParam) {
        super(useParam);
        try {
            this.count = Integer.parseInt(useParam[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
        }
    }

    @Override
    public boolean useItem(UseItemParams params) {
        return params
                .player
                .getInventory()
                .addItem(this.i, this.count * params.count, ActionReason.PlayerUseItem);
    }
}
