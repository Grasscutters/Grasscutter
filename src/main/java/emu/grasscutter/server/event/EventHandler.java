package emu.grasscutter.server.event;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.utils.EventConsumer;

public final class EventHandler<T extends Event> {
    private final Class<T> eventClass;
    private EventConsumer<T> listener;
    private HandlerPriority priority;
    private boolean handleCanceled;
    
    public EventHandler(Class<T> eventClass) {
        this.eventClass = eventClass;
    }

    /**
     * Gets which event this handler is handling.
     * @return An event class.
     */
    public Class<T> handles() {
        return this.eventClass;
    }

    /**
     * Returns the callback for the handler.
     * @return A consumer callback.
     */
    public EventConsumer<T> getCallback() {
        return this.listener;
    }

    /**
     * Returns the handler's priority.
     * @return The priority of the handler.
     */
    public HandlerPriority getPriority() {
        return this.priority;
    }

    /**
     * Returns if the handler will ignore cancelled events.
     * @return The ignore cancelled state.
     */
    public boolean ignoresCanceled() {
        return this.handleCanceled;
    }

    /**
     * Sets the callback method for when the event is invoked.
     * @param listener An event handler method.
     * @return Method chaining.
     */
    public EventHandler<T> listener(EventConsumer<T> listener) {
        this.listener = listener; return this;
    }

    /**
     * Changes the handler's priority in handling events.
     * @param priority The priority of the handler.
     * @return Method chaining.
     */
    public EventHandler<T> priority(HandlerPriority priority) {
        this.priority = priority; return this;
    }

    /**
     * Sets if the handler will ignore cancelled events.
     * @param ignore If the handler should ignore cancelled events.
     * @return Method chaining.
     */
    public EventHandler<T> ignore(boolean ignore) {
        this.handleCanceled = ignore; return this;
    }

    /**
     * Registers the handler into the PluginManager.
     */
    public void register() {
        Grasscutter.getPluginManager().registerListener(this);
    }
}