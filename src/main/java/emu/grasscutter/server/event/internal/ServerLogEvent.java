package emu.grasscutter.server.event.internal;

import ch.qos.logback.classic.spi.ILoggingEvent;
import emu.grasscutter.server.event.types.ServerEvent;

public class ServerLogEvent extends ServerEvent {
    ILoggingEvent loggingEvent;
    String consoleMessage;

    public ServerLogEvent(Type type, ILoggingEvent loggingEvent, String consoleMessage) {
        super(type);
        this.loggingEvent = loggingEvent;
        this.consoleMessage = consoleMessage;
    }

    public ILoggingEvent getLoggingEvent() {
        return this.loggingEvent;
    }

    public String getConsoleMessage() {
        return this.consoleMessage;
    }
}
