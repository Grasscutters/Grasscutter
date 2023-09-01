package emu.grasscutter.data.excels;

import emu.grasscutter.data.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@ResourceType(name = "PersonalLineExcelConfigData.json")
@Getter
@Setter // TODO: remove setters next API break
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonalLineData extends GameResource {
    @Getter(onMethod_ = @Override)
    int id;

    int avatarID;
    List<Integer> preQuestId;
    int startQuestId;
    int chapterId;
}
