package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseCombineItem extends ItemUseInt {
    private int resultId = 0;
    private int resultCount = 1;

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_COMBINE_ITEM;
    }

    public ItemUseCombineItem(String[] useParam) {
        super(useParam);
        try {
            this.resultId = Integer.parseInt(useParam[1]);
        } catch (NumberFormatException ignored) {}
        try {
            this.resultCount = Integer.parseInt(useParam[2]);
        } catch (NumberFormatException ignored) {}
    }

    @Override
    public boolean useItem(UseItemParams params) {
        if (params.count != this.i) return false;  // Wrong amount of fragments supplied!
        return params.player.getInventory().addItem(this.resultId, this.resultCount);
    }
}
