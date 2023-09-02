package emu.grasscutter.game.dungeons;

import emu.grasscutter.game.dungeons.enums.DungeonPassConditionType;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface DungeonValue {
    DungeonPassConditionType value();
}
