package emu.grasscutter.data.excels;

import java.util.List;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;

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
        return animalId;
    }
    public int getAnimalId(){
        return animalId;
    }
    public String getEntityType(){
        return entityType;
    }
    public GatherItem gatherItem(){
        return gatherItemId.get(0);
    }
    public static class GatherItem{
        private int id;
        private int count;
        public int getId(){
            return id;
        }
        public int getCount(){
            return count;
        }
    }
}
