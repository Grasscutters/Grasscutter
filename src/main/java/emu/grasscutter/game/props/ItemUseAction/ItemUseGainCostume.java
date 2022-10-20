package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

import emu.grasscutter.data.GameData;

public class ItemUseGainCostume extends ItemUseInt {
    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_GAIN_COSTUME;
    }

    public ItemUseGainCostume(String[] useParam) {
        super(useParam);
    }

    @Override
    public boolean useItem(UseItemParams params) {
        if (GameData.getAvatarCostumeDataMap().containsKey(this.i)) {
            params.player.addCostume(this.i);
        }
        return true;
    }
}
