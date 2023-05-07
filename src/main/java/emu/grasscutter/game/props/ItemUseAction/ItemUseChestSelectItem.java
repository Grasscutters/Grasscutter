package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseChestSelectItem extends ItemUseSelectItems {
    private final int[] optionItemCounts;

    public ItemUseChestSelectItem(String[] useParam) {
        String[] options = useParam[0].split(",");
        this.optionItemIds = new int[options.length];
        this.optionItemCounts = new int[options.length];
        for (int i = 0; i < options.length; i++) {
            String[] optionParts = options[i].split(":");
            try {
                this.optionItemIds[i] = Integer.parseInt(optionParts[0]);
            } catch (NumberFormatException ignored) {
                this.optionItemIds[i] = INVALID;
            }
            try {
                this.optionItemCounts[i] = Integer.parseInt(optionParts[1]);
            } catch (NumberFormatException ignored) {
                this.optionItemCounts[i] = INVALID;
            }
        }
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_CHEST_SELECT_ITEM;
    }

    @Override
    protected int getItemCount(int index) {
        if ((optionItemCounts == null) || (index < 0) || (index > optionItemCounts.length))
            return INVALID;
        return this.optionItemCounts[index];
    }
}
