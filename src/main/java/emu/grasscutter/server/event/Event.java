package emu.grasscutter.server.event;

import emu.grasscutter.Grasscutter;

/** A generic server event. */
public abstract class Event {
    private boolean cancelled = false;

    /** Return the cancelled state of the event. */
    public boolean isCanceled() {
        return this.cancelled;
    }

    /** Cancels the event if possible. */
    public void cancel() {
        if (this instanceof Cancellable) this.cancelled = true;
        else throw new UnsupportedOperationException("Event is not cancellable.");
    }

    /**
     * Pushes this event to all listeners.
     *
     * @return True if execution should continue. False if execution should cancel.
     */
    public boolean call() {
        var pluginManager = Grasscutter.getPluginManager();
        if (pluginManager != null) pluginManager.invokeEvent(this);

        return !this.isCanceled();
    }
}
