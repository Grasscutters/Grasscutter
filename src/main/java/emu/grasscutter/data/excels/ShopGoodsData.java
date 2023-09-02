package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
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

    @SerializedName(
            value = "subTabId",
            alternate = {"secondarySheetId"})
    private int subTabId;

    private String refreshType;
    private transient ShopInfo.ShopRefreshType refreshTypeEnum;

    private int refreshParam;

    @Override
    public void onLoad() {
        if (this.refreshType == null) this.refreshTypeEnum = ShopInfo.ShopRefreshType.NONE;
        else {
            this.refreshTypeEnum =
                    switch (this.refreshType) {
                        case "SHOP_REFRESH_DAILY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_DAILY;
                        case "SHOP_REFRESH_WEEKLY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_WEEKLY;
                        case "SHOP_REFRESH_MONTHLY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_MONTHLY;
                        default -> ShopInfo.ShopRefreshType.NONE;
                    };
        }
    }

    @Override
    public int getId() {
        return getGoodsId();
    }

    public int getGoodsId() {
        return goodsId;
    }

    public int getShopType() {
        return shopType;
    }

    public int getItemId() {
        return itemId;
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getCostScoin() {
        return costScoin;
    }

    public int getCostHcoin() {
        return costHcoin;
    }

    public int getCostMcoin() {
        return costMcoin;
    }

    public List<ItemParamData> getCostItems() {
        return costItems;
    }

    public int getMinPlayerLevel() {
        return minPlayerLevel;
    }

    public int getMaxPlayerLevel() {
        return maxPlayerLevel;
    }

    public int getBuyLimit() {
        return buyLimit;
    }

    public int getSubTabId() {
        return subTabId;
    }

    public ShopInfo.ShopRefreshType getRefreshType() {
        return refreshTypeEnum;
    }

    public int getRefreshParam() {
        return refreshParam;
    }
}
