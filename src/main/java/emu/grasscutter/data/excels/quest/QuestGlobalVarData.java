package emu.grasscutter.data.excels.quest;

import emu.grasscutter.data.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ResourceType(name = "QuestGlobalVarConfigData.json")
@EqualsAndHashCode(callSuper = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class QuestGlobalVarData extends GameResource {
    int id;
    int defaultValue;
}
