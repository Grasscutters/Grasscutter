package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import emu.grasscutter.game.shop.ShopType;
import lombok.Getter;

import java.util.List;

@ResourceType(name = "ActivityShopOverallExcelConfigData.json")
public class ActivityShopData extends GameResource {
    @Getter
    private int scheduleId;
    @Getter
    private ShopType shopType;
    @Getter
    private List<Integer> sheetList;


    @Override
    public int getId() {
        return getShopTypeId();
    }

    public int getShopTypeId() {
        if (this.shopType == null)
            this.shopType = ShopType.SHOP_TYPE_NONE;
        return shopType.shopTypeId;
    }
}
