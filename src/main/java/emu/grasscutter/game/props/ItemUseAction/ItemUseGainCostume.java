package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarCostumeData;

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
        AvatarCostumeData costumeData = GameData.getAvatarCostumeDataItemIdMap().get(this.i);
        if (costumeData != null && !params.player.getCostumeList().contains(costumeData.getId())) {
            params.player.addCostume(costumeData.getId());
        }
        return true;
    }
}
