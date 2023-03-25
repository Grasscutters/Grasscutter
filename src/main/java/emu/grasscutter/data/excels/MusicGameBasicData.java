package emu.grasscutter.data.excels;

import emu.grasscutter.data.GameResource;
import emu.grasscutter.data.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "MusicGameBasicConfigData.json")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MusicGameBasicData extends GameResource {
    @Getter(onMethod_ = @Override)
    int id;
    int musicID;
    int musicLevel;
}
