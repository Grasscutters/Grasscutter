package emu.grasscutter.utils;

import emu.grasscutter.server.event.Event;

public interface EventConsumer<T extends Event> {
    void consume(T event);
}
