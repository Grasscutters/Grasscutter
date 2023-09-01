package emu.grasscutter.game.quest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestEncryptionKey {
    int mainQuestId;
    long encryptionKey;
}
