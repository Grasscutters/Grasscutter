package emu.grasscutter.game.props.ItemUseAction;

import emu.grasscutter.game.props.ItemUseOp;

public class ItemUseGrantSelectReward extends ItemUseSelectItems {
    public ItemUseGrantSelectReward(String[] useParam) {
        String[] options = useParam[0].split(",");
        this.optionItemIds = new int[options.length];
        for (int i = 0; i < options.length; i++) {
            try {
                this.optionItemIds[i] = Integer.parseInt(options[i]);
            } catch (NumberFormatException ignored) {
                this.optionItemIds[i] = INVALID;
            }
        }
    }

    @Override
    public ItemUseOp getItemUseOp() {
        return ItemUseOp.ITEM_USE_ADD_SELECT_ITEM;
    }
}
