package emu.grasscutter.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import emu.grasscutter.Grasscutter;
import org.jline.reader.LineReader;

import java.util.Arrays;

public class JlineLogbackAppender extends ConsoleAppender<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent eventObject) {
        if (!started) {
            return;
        }
        Arrays.stream(
                new String(encoder.encode(eventObject)).split("\n\r")
        ).forEach(Grasscutter.getConsole()::printAbove);
    }
}
