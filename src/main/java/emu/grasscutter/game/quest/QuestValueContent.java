package emu.grasscutter.game.quest;

import emu.grasscutter.game.quest.enums.QuestContent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface QuestValueContent {
    QuestContent value();
}
