package emu.grasscutter.game.shop;

import emu.grasscutter.data.common.ItemParamData;
import emu.grasscutter.data.excels.ShopGoodsData;
import java.util.*;
import lombok.*;

public class ShopInfo {
    @Getter @Setter private int goodsId = 0;
    @Getter @Setter private ItemParamData goodsItem;
    @Getter @Setter private int scoin = 0;
    @Getter @Setter private List<ItemParamData> costItemList;
    @Getter @Setter private int boughtNum = 0;
    @Getter @Setter private int buyLimit = 0;
    @Getter @Setter private int beginTime = 0;
    @Getter @Setter private int endTime = 1924992000;
    @Getter @Setter private int minLevel = 0;
    @Getter @Setter private int maxLevel = 61;
    @Getter @Setter private List<Integer> preGoodsIdList = new ArrayList<>();
    @Getter @Setter private int mcoin = 0;
    @Getter @Setter private int hcoin = 0;
    @Getter @Setter private int disableType = 0;
    @Getter @Setter private int secondarySheetId = 0;

    private String refreshType;
    @Setter private transient ShopRefreshType shopRefreshType;
    @Getter @Setter private int shopRefreshParam;

    public ShopInfo(ShopGoodsData sgd) {
        this.goodsId = sgd.getGoodsId();
        this.goodsItem = new ItemParamData(sgd.getItemId(), sgd.getItemCount());
        this.scoin = sgd.getCostScoin();
        this.mcoin = sgd.getCostMcoin();
        this.hcoin = sgd.getCostHcoin();
        this.buyLimit = sgd.getBuyLimit();

        this.minLevel = sgd.getMinPlayerLevel();
        this.maxLevel = sgd.getMaxPlayerLevel();
        this.costItemList =
                sgd.getCostItems().stream()
                        .filter(x -> x.getId() != 0)
                        .map(x -> new ItemParamData(x.getId(), x.getCount()))
                        .toList();
        this.secondarySheetId = sgd.getSubTabId();
        this.shopRefreshType = sgd.getRefreshType();
        this.shopRefreshParam = sgd.getRefreshParam();
    }

    public ShopRefreshType getShopRefreshType() {
        if (refreshType == null) return ShopRefreshType.NONE;
        return switch (refreshType) {
            case "SHOP_REFRESH_DAILY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_DAILY;
            case "SHOP_REFRESH_WEEKLY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_WEEKLY;
            case "SHOP_REFRESH_MONTHLY" -> ShopInfo.ShopRefreshType.SHOP_REFRESH_MONTHLY;
            default -> ShopInfo.ShopRefreshType.NONE;
        };
    }

    private boolean evaluateVirtualCost(ItemParamData item) {
        return switch (item.getId()) {
            case 201 -> {
                this.hcoin += item.getCount();
                yield true;
            }
            case 203 -> {
                this.mcoin += item.getCount();
                yield true;
            }
            default -> false;
        };
    }

    public void removeVirtualCosts() {
        if (this.costItemList != null) this.costItemList.removeIf(item -> evaluateVirtualCost(item));
    }

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
}
