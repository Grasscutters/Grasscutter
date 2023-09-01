package emu.grasscutter.game.talk;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface TalkValueExec {
    TalkExec value();
}
