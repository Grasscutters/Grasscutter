package emu.grasscutter.game.shop;

import java.util.ArrayList;
import java.util.List;

public class ShopTable {
    private int shopId;
    private List<ShopInfo> items = new ArrayList<>();

    public int getShopId() {
        return this.shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public List<ShopInfo> getItems() {
        return this.items;
    }

    public void setItems(List<ShopInfo> items) {
        this.items = items;
    }
}
