package emu.grasscutter.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import emu.grasscutter.server.event.internal.ServerLogEvent;
import emu.grasscutter.server.event.types.ServerEvent;

public class ServerLogEventAppender extends AppenderBase<ILoggingEvent> {
    @Override
    protected void append(ILoggingEvent event) {
        ServerLogEvent sle = new ServerLogEvent(ServerEvent.Type.GAME, event);
        sle.call();
    }
}
