package emu.grasscutter.game.shop;

import emu.grasscutter.data.common.ItemParamData;

import java.util.ArrayList;
import java.util.List;

public class ShopChestTable {
    private int itemId;
    private List<ItemParamData> containsItem = new ArrayList<>();

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public List<ItemParamData> getContainsItem() {
        return containsItem;
    }

    public void setContainsItem(List<ItemParamData> containsItem) {
        this.containsItem = containsItem;
    }
}
