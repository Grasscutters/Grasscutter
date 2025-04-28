package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.ResourceType.LoadPriority;
import emu.grasscutter.data.common.*;
import java.util.Arrays;
import lombok.Getter;

@ResourceType(name = "RewardPreviewExcelConfigData.json", loadPriority = LoadPriority.HIGH)
public class RewardPreviewData extends GameResource {
    @Getter(onMethod = @__(@Override))
    private int id;

    private ItemParamStringData[] previewItems;
    private ItemParamData[] previewItemsArray;

    public ItemParamData[] getPreviewItems() {
        return previewItemsArray;
    }

    @Override
    public void onLoad() {
        if (this.previewItems != null && this.previewItems.length > 0) {
            this.previewItemsArray =
                    Arrays.stream(this.previewItems)
                            .filter(d -> d.getId() > 0 && d.getCount() != null && !d.getCount().isEmpty())
                            .map(ItemParamStringData::toItemParamData)
                            .toArray(ItemParamData[]::new);
        } else {
            this.previewItemsArray = new ItemParamData[0];
        }
    }
}
