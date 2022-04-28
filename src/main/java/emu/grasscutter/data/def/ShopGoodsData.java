package emu.grasscutter.data.def;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.data.common.ItemParamData;

import java.util.List;

@ResourceType(name = "ShopGoodsExcelConfigData.json")
public class ShopGoodsData extends GameResource {
    private int GoodsId;
    private int ShopType;
    private int ItemId;

    private int ItemCount;

    private int CostScoin;
    private int CostHcoin;
    private int CostMcoin;

    private List<ItemParamData> CostItems;
    private int MinPlayerLevel;
    private int MaxPlayerLevel;

    private int BuyLimit;
    private int SubTabId;

    @Override
    public int getId() {
        return getGoodsId();
    }

    public int getGoodsId() {
        return GoodsId;
    }

    public int getShopType() {
        return ShopType;
    }

    public int getItemId() {
        return ItemId;
    }

    public int getItemCount() {
        return ItemCount;
    }

    public int getCostScoin() {
        return CostScoin;
    }

    public int getCostHcoin() {
        return CostHcoin;
    }

    public int getCostMcoin() {
        return CostMcoin;
    }

    public List<ItemParamData> getCostItems() {
        return CostItems;
    }

    public int getMinPlayerLevel() {
        return MinPlayerLevel;
    }

    public int getMaxPlayerLevel() {
        return MaxPlayerLevel;
    }

    public int getBuyLimit() {
        return BuyLimit;
    }

    public int getSubTabId() {
        return SubTabId;
    }
}
