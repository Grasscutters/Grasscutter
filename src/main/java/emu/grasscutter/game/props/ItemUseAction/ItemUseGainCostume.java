package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseGainCostume extends ItemUseInt {
    public ItemUseGainCostume(String[] useParam) {
        super(useParam);
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_GAIN_COSTUME;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        if (GameData.getAvatarCostumeDataMap().containsKey(this.i)) {
            params.player.addCostume(this.i);
        }
        return true;
    }
}
