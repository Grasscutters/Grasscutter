package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import emu.grasscutter.data.common.ItemParamData;
import java.util.List;
import lombok.Getter;

@ResourceType(
        name = "EnvAnimalGatherExcelConfigData.json",
        loadPriority = ResourceType.LoadPriority.LOW)
public class EnvAnimalGatherConfigData extends GameResource {
    @Getter private int animalId;
    @Getter private String entityType;
    private List<ItemParamData> gatherItemId;
    private String excludeWeathers;
    private int aliveTime;
    private int escapeTime;
    private int escapeRadius;

    @Override
    public int getId() {
        return animalId;
    }

    public ItemParamData getGatherItem() {
        return !gatherItemId.isEmpty() ? gatherItemId.get(0) : null;
    }
}
