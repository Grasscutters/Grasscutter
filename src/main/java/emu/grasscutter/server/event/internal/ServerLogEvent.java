package emu.grasscutter.server.event.internal;

import ch.qos.logback.classic.spi.ILoggingEvent;
import emu.grasscutter.server.event.types.ServerEvent;
import lombok.Getter;

@Getter
public final class ServerLogEvent extends ServerEvent {
    private final ILoggingEvent loggingEvent;
    private final String consoleMessage;

    public ServerLogEvent(ILoggingEvent loggingEvent, String consoleMessage) {
        super(Type.GAME);

        this.loggingEvent = loggingEvent;
        this.consoleMessage = consoleMessage;
    }
}
