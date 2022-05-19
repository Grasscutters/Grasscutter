package emu.grasscutter.server.event.internal;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import emu.grasscutter.server.event.types.ServerEvent;

public class ServerLogEvent extends ServerEvent {
    ILoggingEvent loggingEvent;

    public ServerLogEvent(Type type, ILoggingEvent loggingEvent) {
        super(type);
        this.loggingEvent = loggingEvent;
    }

    public ILoggingEvent getLoggingEvent() {
        return loggingEvent;
    }
}
