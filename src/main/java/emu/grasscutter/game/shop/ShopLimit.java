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

    public int getNextRefreshTime() {
        return nextRefreshTime;
    }

    public void setNextRefreshTime(int nextRefreshTime) {
        this.nextRefreshTime = nextRefreshTime;
    }

    public int getHasBoughtInPeriod() {
        return hasBoughtInPeriod;
    }

    public void setHasBoughtInPeriod(int hasBoughtInPeriod) {
        this.hasBoughtInPeriod = hasBoughtInPeriod;
    }

    private int shopGoodId;
    private int hasBought;
    private int hasBoughtInPeriod = 0;
    private int nextRefreshTime = 0;
}
