package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseAddCurStamina extends ItemUseInt {
    public ItemUseAddCurStamina(String[] useParam) {
        super(useParam);
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_CUR_STAMINA;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        return params.player.getStaminaManager().addCurrentStamina(this.i);
    }
}
