package emu.grasscutter.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.spi.DeferredProcessingAware;
import ch.qos.logback.core.status.ErrorStatus;
import emu.grasscutter.server.event.internal.ServerLogEvent;
import emu.grasscutter.server.event.types.ServerEvent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ServerLogEventAppender<E> extends AppenderBase<E> {
    protected Encoder<E> encoder;

    @Override
    protected void append(E event) {
        byte[] byteArray = this.encoder.encode(event);
        ServerLogEvent sle = new ServerLogEvent((ILoggingEvent) event, new String(byteArray, StandardCharsets.UTF_8));
        sle.call();
    }

    public Encoder<E> getEncoder() {
        return this.encoder;
    }

    public void setEncoder(Encoder<E> encoder) {
        this.encoder = encoder;
    }
}
