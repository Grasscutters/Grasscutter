package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.props.ActionReason;

public abstract class ItemUseSelectItems extends ItemUseAction {
    protected static final int INVALID = -1;
    protected int[] optionItemIds;

    protected int getItemId(int index) {
        if ((optionItemIds == null) || (index < 0) || (index > optionItemIds.length)) return INVALID;
        return this.optionItemIds[index];
    }

    protected int getItemCount(int index) {
        return 1;
    }

    protected GameItem getItemStack(int index, int useCount) {
        int id = this.getItemId(index);
        int count = this.getItemCount(index);
        if (id == INVALID || count == INVALID) return null;

        var item = GameData.getItemDataMap().get(id);
        if (item == null) return null;

        return new GameItem(item, count * useCount);
    }

    @Override
    public boolean useItem(UseItemParams params) {
        var itemStack = this.getItemStack(params.optionId - 1, params.count);
        if (itemStack == null) return false;

        return params.player.getInventory().addItem(itemStack, ActionReason.Shop);
    }
}
