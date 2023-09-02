package emu.grasscutter.data.excels.activity;

import emu.grasscutter.data.*;
import emu.grasscutter.game.shop.ShopType;
import java.util.List;
import lombok.Getter;

@ResourceType(name = "ActivityShopOverallExcelConfigData.json")
public class ActivityShopData extends GameResource {
    @Getter private int scheduleId;
    @Getter private ShopType shopType;
    @Getter private List<Integer> sheetList;

    @Override
    public int getId() {
        return getShopTypeId();
    }

    public int getShopTypeId() {
        if (this.shopType == null) this.shopType = ShopType.SHOP_TYPE_NONE;
        return shopType.shopTypeId;
    }
}
