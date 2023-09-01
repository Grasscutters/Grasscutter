package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import lombok.*;
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
