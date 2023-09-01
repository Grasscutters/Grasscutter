package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import lombok.*;
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
}
