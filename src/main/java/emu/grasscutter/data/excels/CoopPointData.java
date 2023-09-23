package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "CoopPointExcelConfigData.json")
@Getter
@Setter // TODO: remove setters next API break
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoopPointData extends GameResource {
    @Getter(onMethod_ = @Override)
    int id;

    int chapterId;
    String type;
    int acceptQuest;
    int[] postPointList;
    //    int pointNameTextMapHash;
    //    int pointDecTextMapHash;
    int pointPosId;
    //    long photoMaleHash;
    //    long photoFemaleHash;
}
