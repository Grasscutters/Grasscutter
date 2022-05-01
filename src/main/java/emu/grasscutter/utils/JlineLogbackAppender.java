package emu.grasscutter.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import emu.grasscutter.Grasscutter;

public class JlineLogbackAppender extends ConsoleAppender<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent eventObject) {
        if (!started) {
            return;
        }
        Grasscutter.getConsole().printAbove(new String(encoder.encode(eventObject)));
    }
}
