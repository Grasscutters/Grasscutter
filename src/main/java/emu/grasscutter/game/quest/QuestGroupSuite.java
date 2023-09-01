package emu.grasscutter.game.quest;

import dev.morphia.annotations.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder(builderMethodName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestGroupSuite {
    int scene;
    int group;
    int suite;
}
