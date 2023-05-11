package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseUnlockHomeBgm extends ItemUseInt {
    public ItemUseUnlockHomeBgm(String[] useParam) {
        super(useParam);
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_UNLOCK_HOME_BGM;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        params.player.getHome().addUnlockedHomeBgm(this.i);
        return true; // Probably best to remove the item even if the bgm was already unlocked.
    }
}
