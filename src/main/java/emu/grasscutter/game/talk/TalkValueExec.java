package emu.grasscutter.game.talk;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TalkValueExec {
    TalkExec value();
}
