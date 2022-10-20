package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarFlycloakData;

public class ItemUseGainFlycloak extends ItemUseInt {
    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_GAIN_FLYCLOAK;
    }

    public ItemUseGainFlycloak(String[] useParam) {
        super(useParam);
    }

    @Override
    public boolean useItem(UseItemParams params) {
        AvatarFlycloakData flycloakData = GameData.getAvatarFlycloakDataMap().get(this.i);
        if (flycloakData != null && !params.player.getFlyCloakList().contains(this.i)) {
            params.player.addFlycloak(this.i);
        }
        return true;
    }
}
