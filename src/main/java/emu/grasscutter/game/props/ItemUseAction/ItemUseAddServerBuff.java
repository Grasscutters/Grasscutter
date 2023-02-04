package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseAddServerBuff extends ItemUseInt {
    private int duration = 0;

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_SERVER_BUFF;
    }

    public ItemUseAddServerBuff(String[] useParam) {
        super(useParam);
        try {
            this.duration = Integer.parseInt(useParam[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {}
    }

    @Override
    public boolean useItem(UseItemParams params) {
        return params.player.getBuffManager().addBuff(this.i, this.duration, params.targetAvatar);
    }
}
