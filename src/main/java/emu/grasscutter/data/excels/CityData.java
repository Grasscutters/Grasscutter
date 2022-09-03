package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@ResourceType(name = "CityConfigData.json", loadPriority = ResourceType.LoadPriority.HIGH)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CityData extends GameResource {
    int cityId;
    int sceneId;
    List<Integer> areaIdVec;

    @Override
    public int getId() {
        return this.cityId;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }
}
