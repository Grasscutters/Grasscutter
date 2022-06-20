package emu.grasscutter.server.event;

import emu.grasscutter.Grasscutter;

/**
 * A generic server event.
 */
public abstract class Event {
    private boolean cancelled = false;

    /**
     * Return the cancelled state of the event.
     */
    public boolean isCanceled() {
        return this.cancelled;
    }

    /**
     * Cancels the event if possible.
     */
    public void cancel() {
        if (this instanceof Cancellable)
            this.cancelled = true;
    }

    /**
     * Pushes this event to all listeners.
     */
    public void call() {
        Grasscutter.getPluginManager().invokeEvent(this);
    }
}
