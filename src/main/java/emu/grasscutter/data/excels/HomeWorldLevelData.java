package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ResourceType(name = {"HomeworldLevelExcelConfigData.json"})
public class HomeWorldLevelData extends GameResource {

    int level;
    int exp;
    int homeCoinStoreLimit;
    int homeFetterExpStoreLimit;
    int rewardId;
    int furnitureMakeSlotCount;
    int outdoorUnlockBlockCount;
    int freeUnlockModuleCount;
    int deployNpcCount;
    int limitShopGoodsCount;
    List<String> levelFuncs;

    @Override
    public int getId() {
        return this.level;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }
}
