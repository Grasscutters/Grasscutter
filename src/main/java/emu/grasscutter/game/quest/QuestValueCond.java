package emu.grasscutter.game.quest;

import emu.grasscutter.game.quest.enums.QuestCond;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface QuestValueCond {
    QuestCond value();
}
