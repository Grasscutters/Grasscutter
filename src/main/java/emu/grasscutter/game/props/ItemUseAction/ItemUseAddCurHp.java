package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseAddCurHp extends ItemUseInt {
    private final String icon;

    public ItemUseAddCurHp(String[] useParam) {
        super(useParam);
        this.icon = useParam[1];
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_CUR_HP;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        return (params.targetAvatar.getAsEntity().heal(params.count * this.i) > 0.01);
    }
}
