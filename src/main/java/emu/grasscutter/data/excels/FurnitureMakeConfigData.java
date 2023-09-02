package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ResourceType(name = {"FurnitureMakeExcelConfigData.json"})
public class FurnitureMakeConfigData extends GameResource {

    int configID;
    int furnitureItemID;
    int count;
    int exp;
    List<ItemParamData> materialItems;
    int makeTime;
    int maxAccelerateTime;
    int quickFetchMaterialNum;

    @Override
    public int getId() {
        return configID;
    }

    @Override
    public void onLoad() {
        this.materialItems = materialItems.stream().filter(x -> x.getId() > 0).toList();
    }
}
