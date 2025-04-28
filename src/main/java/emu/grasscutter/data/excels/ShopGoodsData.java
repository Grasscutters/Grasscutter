package emu.grasscutter.data.excels;

import com.google.gson.annotations.SerializedName;
import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.game.shop.ShopInfo;
import java.util.List;
import lombok.Getter;

@ResourceType(name = "ShopGoodsExcelConfigData.json")
public class ShopGoodsData extends GameResource {
    @Getter private int goodsId;
    @Getter private int shopType;
    @Getter private int itemId;

    @Getter private int itemCount;

    @Getter private int costScoin;
    @Getter private int costHcoin;
    @Getter private int costMcoin;

    @Getter private List<ItemParamData> costItems;
    @Getter private int minPlayerLevel;
    @Getter private int maxPlayerLevel;

    @Getter private int buyLimit;

    @Getter
    @SerializedName(
            value = "subTabId",
            alternate = {"secondarySheetId"})
    private int subTabId;

    private String refreshType;
    private transient ShopInfo.ShopRefreshType refreshTypeEnum;

    @Getter private int refreshParam;

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

    public ShopInfo.ShopRefreshType getRefreshType() {
        return refreshTypeEnum;
    }
}
