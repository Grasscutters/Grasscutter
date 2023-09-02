package emu.grasscutter.game.quest;

import emu.grasscutter.game.quest.enums.QuestExec;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface QuestValueExec {
    QuestExec value();
}
