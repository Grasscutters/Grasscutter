package emu.grasscutter.game.shop;

import java.util.*;

public class ShopTable {
    private int shopId;
    private List<ShopInfo> items = new ArrayList<>();

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public List<ShopInfo> getItems() {
        return items;
    }

    public void setItems(List<ShopInfo> items) {
        this.items = items;
    }
}
