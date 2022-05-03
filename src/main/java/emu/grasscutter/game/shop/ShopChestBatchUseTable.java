package emu.grasscutter.game.shop;

import java.util.ArrayList;
import java.util.List;

public class ShopChestBatchUseTable {
    private int itemId;
    private List<Integer> optionItem = new ArrayList<>();

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public List<Integer> getOptionItem() {
        return optionItem;
    }

    public void setOptionItem(List<Integer> optionItem) {
        this.optionItem = optionItem;
    }
}
