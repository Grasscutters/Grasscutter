package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseUnlockPaidBattlePassNormal extends ItemUseAction {
    public ItemUseUnlockPaidBattlePassNormal(String[] useParam) {}

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_UNLOCK_PAID_BATTLE_PASS_NORMAL;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        // TODO: add paid BP
        // return params.player.getBattlePassManager().setPaid(true);
        return false;
    }
}
