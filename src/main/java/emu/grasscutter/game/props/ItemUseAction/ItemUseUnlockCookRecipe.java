package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseUnlockCookRecipe extends ItemUseInt {
    public ItemUseUnlockCookRecipe(String[] useParam) {
        super(useParam);
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_UNLOCK_COOK_RECIPE;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        return true;
    }

    @Override
    public boolean postUseItem(UseItemParams params) {
        return params.player.getCookingManager().unlockRecipe(this.i);
    }
}
