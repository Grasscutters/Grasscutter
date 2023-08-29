package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseUnlockHomeModule extends ItemUseInt {
    public ItemUseUnlockHomeModule(String[] useParam) {
        super(useParam);
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_UNLOCK_HOME_MODULE;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        params.player.addRealmList(this.i);
        return true;
    }
}
