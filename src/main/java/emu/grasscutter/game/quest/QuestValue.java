package emu.grasscutter.game.quest;

import emu.grasscutter.game.quest.enums.QuestTrigger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface QuestValue {
    QuestTrigger value();
}
