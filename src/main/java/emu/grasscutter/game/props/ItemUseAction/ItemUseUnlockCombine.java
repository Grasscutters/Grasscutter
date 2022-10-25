package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseUnlockCombine extends ItemUseInt {
    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_UNLOCK_COMBINE;
    }

    public ItemUseUnlockCombine(String[] useParam) {
        super(useParam);
    }

    @Override
    public boolean useItem(UseItemParams params) {
        return true;
    }

    @Override
    public boolean postUseItem(UseItemParams params) {
        return params.player.getServer().getCombineSystem().unlockCombineDiagram(params.player, this.i);
    }
}
