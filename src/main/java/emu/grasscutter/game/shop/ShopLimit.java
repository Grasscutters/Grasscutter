package emu.grasscutter.game.shop;

import dev.morphia.annotations.Entity;

@Entity
public class ShopLimit {
    public int getShopGoodId() {
        return this.shopGoodId;
    }

    public void setShopGoodId(int shopGoodId) {
        this.shopGoodId = shopGoodId;
    }

    public int getHasBought() {
        return this.hasBought;
    }

    public void setHasBought(int hasBought) {
        this.hasBought = hasBought;
    }

    public int getNextRefreshTime() {
        return this.nextRefreshTime;
    }

    public void setNextRefreshTime(int nextRefreshTime) {
        this.nextRefreshTime = nextRefreshTime;
    }

    public int getHasBoughtInPeriod() {
        return this.hasBoughtInPeriod;
    }

    public void setHasBoughtInPeriod(int hasBoughtInPeriod) {
        this.hasBoughtInPeriod = hasBoughtInPeriod;
    }

    private int shopGoodId;
    private int hasBought;
    private int hasBoughtInPeriod = 0;
    private int nextRefreshTime = 0;
}
