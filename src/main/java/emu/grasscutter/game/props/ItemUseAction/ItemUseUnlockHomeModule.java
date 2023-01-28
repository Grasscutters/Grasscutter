package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseUnlockHomeModule extends ItemUseInt {
    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_UNLOCK_HOME_MODULE;
    }

    public ItemUseUnlockHomeModule(String[] useParam) {
        super(useParam);
    }

    @Override
    public boolean useItem(UseItemParams params) {
        return true;
    }

    @Override
    public boolean postUseItem(UseItemParams params){

        // Realm 5 will break all homes, so blacklist for now
        if(this.i == 5){
            return false;
        }

        params.player.addRealmList(this.i);
        return true;
    }
}
