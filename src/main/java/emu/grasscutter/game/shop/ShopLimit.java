package emu.grasscutter.game.shop;

import dev.morphia.annotations.Entity;

@Entity
public class ShopLimit {
    public int getShopGoodId() {
        return shopGoodId;
    }

    public void setShopGoodId(int shopGoodId) {
        this.shopGoodId = shopGoodId;
    }

    public int getHasBought() {
        return hasBought;
    }

    public void setHasBought(int hasBought) {
        this.hasBought = hasBought;
    }

    private int shopGoodId;
    private int hasBought;
}
