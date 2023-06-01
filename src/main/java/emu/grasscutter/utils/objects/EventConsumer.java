package emu.grasscutter.utils.objects;

import emu.grasscutter.server.event.Event;

public interface EventConsumer<T extends Event> {
    void consume(T event);
}
