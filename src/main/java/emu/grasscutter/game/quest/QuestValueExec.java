package emu.grasscutter.game.quest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import emu.grasscutter.game.quest.enums.QuestExec;

@Retention(RetentionPolicy.RUNTIME)
public @interface QuestValueExec {
    QuestExec value();
}
