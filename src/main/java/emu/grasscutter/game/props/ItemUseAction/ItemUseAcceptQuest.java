package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseAcceptQuest extends ItemUseInt {
    public ItemUseAcceptQuest(String[] useParam) {
        super(useParam);
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ACCEPT_QUEST;
    }

    @Override
    public boolean useItem(UseItemParams params) {
        return (params.player.getQuestManager().addQuest(this.i) != null);
    }
}
