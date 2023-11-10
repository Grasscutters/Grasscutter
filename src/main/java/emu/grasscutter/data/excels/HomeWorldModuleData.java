package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "HomeworldModuleExcelConfigData.json")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class HomeWorldModuleData extends GameResource {
    int Id;
    boolean isFree;
    int worldSceneId;
    int defaultRoomSceneId;
}
