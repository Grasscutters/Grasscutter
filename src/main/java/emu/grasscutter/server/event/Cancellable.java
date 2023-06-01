package emu.grasscutter.server.event;

/** Implementing this interface marks an event as cancellable. */
public interface Cancellable {
    void cancel();
}
