package emu.grasscutter.game.shop;

import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.def.ShopGoodsData;

import java.util.ArrayList;
import java.util.List;

public class ShopInfo {
    private int goodsId = 0;
    private ItemParamData goodsItem;
    private int scoin = 0;
    private List<ItemParamData> costItemList;
    private int boughtNum = 0;
    private int buyLimit = 0;
    private int beginTime = 0;
    private int endTime = 1924992000;
    private int minLevel = 0;
    private int maxLevel = 61;
    private List<Integer> preGoodsIdList = new ArrayList<>();
    private int mcoin = 0;
    private int hcoin = 0;
    private int disableType = 0;
    private int secondarySheetId = 0;

    private String refreshType;

    public enum ShopRefreshType {
        NONE(0),
        SHOP_REFRESH_DAILY(1),
        SHOP_REFRESH_WEEKLY(2),
        SHOP_REFRESH_MONTHLY(3);

        private final int value;
        ShopRefreshType(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    private transient ShopRefreshType shopRefreshType;
    private int shopRefreshParam;

    public ShopInfo(ShopGoodsData sgd) {
        this.goodsId = sgd.getGoodsId();
        this.goodsItem = new ItemParamData(sgd.getItemId(), sgd.getItemCount());
        this.scoin = sgd.getCostScoin();
        this.mcoin = sgd.getCostMcoin();
        this.hcoin = sgd.getCostHcoin();
        this.buyLimit = sgd.getBuyLimit();

        this.minLevel = sgd.getMinPlayerLevel();
        this.maxLevel = sgd.getMaxPlayerLevel();
        this.costItemList = sgd.getCostItems().stream().filter(x -> x.getId() != 0).map(x -> new ItemParamData(x.getId(), x.getCount())).toList();
        this.secondarySheetId = sgd.getSubTabId();
        this.shopRefreshType = sgd.getRefreshType();
        this.shopRefreshParam = sgd.getRefreshParam();
    }

    public int getHcoin() {
        return hcoin;
    }

    public void setHcoin(int hcoin) {
        this.hcoin = hcoin;
    }

    public List<Integer> getPreGoodsIdList() {
        return preGoodsIdList;
    }

    public void setPreGoodsIdList(List<Integer> preGoodsIdList) {
        this.preGoodsIdList = preGoodsIdList;
    }

    public int getMcoin() {
        return mcoin;
    }

    public void setMcoin(int mcoin) {
        this.mcoin = mcoin;
    }

    public int getDisableType() {
        return disableType;
    }

    public void setDisableType(int disableType) {
        this.disableType = disableType;
    }

    public int getSecondarySheetId() {
        return secondarySheetId;
    }

    public void setSecondarySheetId(int secondarySheetId) {
        this.secondarySheetId = secondarySheetId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public ItemParamData getGoodsItem() {
        return goodsItem;
    }

    public void setGoodsItem(ItemParamData goodsItem) {
        this.goodsItem = goodsItem;
    }

    public int getScoin() {
        return scoin;
    }

    public void setScoin(int scoin) {
        this.scoin = scoin;
    }

    public List<ItemParamData> getCostItemList() {
        return costItemList;
    }

    public void setCostItemList(List<ItemParamData> costItemList) {
        this.costItemList = costItemList;
    }

    public int getBoughtNum() {
        return boughtNum;
    }

    public void setBoughtNum(int boughtNum) {
        this.boughtNum = boughtNum;
    }

    public int getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(int buyLimit) {
        this.buyLimit = buyLimit;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public ShopRefreshType getShopRefreshType() {
        if (refreshType == null)
            return ShopRefreshType.NONE;
        return switch (refreshType) {
            case "SHOP_REFRESH_DAILY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_DAILY;
            case "SHOP_REFRESH_WEEKLY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_WEEKLY;
            case "SHOP_REFRESH_MONTHLY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_MONTHLY;
            default -> ShopInfo.ShopRefreshType.NONE;
        };
    }

    public void setShopRefreshType(ShopRefreshType shopRefreshType) {
        this.shopRefreshType = shopRefreshType;
    }

    public int getShopRefreshParam() {
        return shopRefreshParam;
    }

    public void setShopRefreshParam(int shopRefreshParam) {
        this.shopRefreshParam = shopRefreshParam;
    }
}
