package emu.grasscutter.game.quest;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestEncryptionKey {
    int mainQuestId;
    long encryptionKey;
}
