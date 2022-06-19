package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.shop.ShopInfo;

import java.util.List;

@ResourceType(name = "ShopGoodsExcelConfigData.json")
public class ShopGoodsData extends GameResource {
    private int goodsId;
    private int shopType;
    private int itemId;

    private int itemCount;

    private int costScoin;
    private int costHcoin;
    private int costMcoin;

    private List<ItemParamData> costItems;
    private int minPlayerLevel;
    private int maxPlayerLevel;

    private int buyLimit;
    private int subTabId;

    private String refreshType;
    private transient ShopInfo.ShopRefreshType refreshTypeEnum;

    private int refreshParam;

    @Override
    public void onLoad() {
        if (this.refreshType == null)
            this.refreshTypeEnum = ShopInfo.ShopRefreshType.NONE;
        else {
            this.refreshTypeEnum = switch (this.refreshType) {
                case "SHOP_REFRESH_DAILY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_DAILY;
                case "SHOP_REFRESH_WEEKLY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_WEEKLY;
                case "SHOP_REFRESH_MONTHLY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_MONTHLY;
                default -> ShopInfo.ShopRefreshType.NONE;
            };
        }
    }

    @Override
    public int getId() {
        return this.getGoodsId();
    }

    public int getGoodsId() {
        return this.goodsId;
    }

    public int getShopType() {
        return this.shopType;
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public int getCostScoin() {
        return this.costScoin;
    }

    public int getCostHcoin() {
        return this.costHcoin;
    }

    public int getCostMcoin() {
        return this.costMcoin;
    }

    public List<ItemParamData> getCostItems() {
        return this.costItems;
    }

    public int getMinPlayerLevel() {
        return this.minPlayerLevel;
    }

    public int getMaxPlayerLevel() {
        return this.maxPlayerLevel;
    }

    public int getBuyLimit() {
        return this.buyLimit;
    }

    public int getSubTabId() {
        return this.subTabId;
    }

    public ShopInfo.ShopRefreshType getRefreshType() {
        return this.refreshTypeEnum;
    }

    public int getRefreshParam() {
        return this.refreshParam;
    }
}
