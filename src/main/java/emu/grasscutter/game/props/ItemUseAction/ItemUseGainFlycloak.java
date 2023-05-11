package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseGainFlycloak extends ItemUseInt {
    public ItemUseGainFlycloak(String[] useParam) {
        super(useParam);
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_GAIN_FLYCLOAK;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        if (GameData.getAvatarFlycloakDataMap().containsKey(this.i)) {
            params.player.addFlycloak(this.i);
        }
        return true;
    }
}
