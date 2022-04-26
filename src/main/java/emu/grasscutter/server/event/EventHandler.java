package emu.grasscutter.server.event;

import emu.grasscutter.Grasscutter;

import java.util.function.Consumer;

public final class EventHandler {
    private final Class<? extends Event> event;

    /**
     * Creates an instance of {@link EventHandler} for the specified event.
     * @param event The event to handle.
     * @return An instance of {@link EventHandler}.
     */
    public static EventHandler forEvent(Class<? extends Event> event) {
        return new EventHandler(event);
    }
    
    /**
     * @deprecated Will be replaced with a private constructor instead. Use {@link #forEvent(Class)} instead.
     */
    @Deprecated(forRemoval = true, since = "1.0.1")
    public EventHandler(Class<? extends Event> event) {
        this.event = event;
    }
    
    private Consumer<Event> listener;
    private HandlerPriority priority;
    private boolean handleCanceled;

    /**
     * Gets which event this handler is handling.
     * @return An event class.
     */
    public Class<? extends Event> handles() {
        return this.event;
    }

    /**
     * Returns the callback for the handler.
     * @return A consumer callback.
     */
    public Consumer<Event> getCallback() {
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
    public EventHandler listener(Consumer<Event> listener) {
        this.listener = listener; return this;
    }

    /**
     * Changes the handler's priority in handling events.
     * @param priority The priority of the handler.
     * @return Method chaining.
     */
    public EventHandler priority(HandlerPriority priority) {
        this.priority = priority; return this;
    }

    /**
     * Sets if the handler will ignore cancelled events.
     * @param ignore If the handler should ignore cancelled events.
     * @return Method chaining.
     */
    public EventHandler ignore(boolean ignore) {
        this.handleCanceled = ignore; return this;
    }

    /**
     * Registers the handler into the PluginManager.
     */
    public void register() {
        Grasscutter.getPluginManager().registerListener(this);
    }
}