package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseReliveAvatar extends ItemUseAction {
    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_RELIVE_AVATAR;
    }

    public ItemUseReliveAvatar(String[] useParam) {
    }

    @Override
    public boolean useItem(UseItemParams params) {
        return params.player.getTeamManager().reviveAvatar(params.targetAvatar);
    }
}
