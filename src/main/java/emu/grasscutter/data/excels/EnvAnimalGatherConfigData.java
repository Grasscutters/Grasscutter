package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

import java.util.List;

@ResourceType(name = "EnvAnimalGatherExcelConfigData.json", loadPriority = ResourceType.LoadPriority.LOW)
public class EnvAnimalGatherConfigData extends GameResource {
    private int animalId;
    private String entityType;
    private List<GatherItem> gatherItemId;
    private String excludeWeathers;
    private int aliveTime;
    private int escapeTime;
    private int escapeRadius;

    @Override
    public int getId() {
        return this.animalId;
    }

    public int getAnimalId() {
        return this.animalId;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public GatherItem gatherItem() {
        return this.gatherItemId.get(0);
    }

    public static class GatherItem {
        private int id;
        private int count;

        public int getId() {
            return this.id;
        }

        public int getCount() {
            return this.count;
        }
    }
}
