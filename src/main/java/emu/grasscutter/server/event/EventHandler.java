package emu.grasscutter.server.event;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.plugin.Plugin;
import emu.grasscutter.utils.objects.EventConsumer;

public final class EventHandler<T extends Event> {
    /**
     * Create and register a new event handler.
     *
     * @param plugin The plugin handling the event.
     * @param eventClass The event class.
     * @param listener The event handler function.
     */
    public static <T extends Event> void newHandler(
            Plugin plugin, Class<T> eventClass, EventConsumer<T> listener) {
        new EventHandler<>(eventClass)
                .priority(HandlerPriority.NORMAL)
                .listener(listener)
                .register(plugin);
    }

    /**
     * Create and register a new event handler.
     *
     * @param plugin The plugin handling the event.
     * @param eventClass The event class.
     * @param listener The event handler function.
     * @param priority The handler's priority.
     */
    public static <T extends Event> void newHandler(
            Plugin plugin, Class<T> eventClass, EventConsumer<T> listener, HandlerPriority priority) {
        new EventHandler<>(eventClass).listener(listener).priority(priority).register(plugin);
    }

    /**
     * Create and register a new event handler.
     *
     * @param plugin The plugin handling the event.
     * @param eventClass The event class.
     * @param listener The event handler function.
     * @param priority The handler's priority.
     * @param handleCanceled Should this handler execute on canceled events?
     */
    public static <T extends Event> void newHandler(
            Plugin plugin,
            Class<T> eventClass,
            EventConsumer<T> listener,
            HandlerPriority priority,
            boolean handleCanceled) {
        new EventHandler<>(eventClass)
                .listener(listener)
                .priority(priority)
                .ignore(handleCanceled)
                .register(plugin);
    }

    private final Class<T> eventClass;
    private EventConsumer<T> listener;
    private HandlerPriority priority;
    private boolean handleCanceled;
    private Plugin plugin;

    public EventHandler(Class<T> eventClass) {
        this.eventClass = eventClass;
    }

    /**
     * Gets which event this handler is handling.
     *
     * @return An event class.
     */
    public Class<T> handles() {
        return this.eventClass;
    }

    /**
     * Returns the callback for the handler.
     *
     * @return A consumer callback.
     */
    public EventConsumer<T> getCallback() {
        return this.listener;
    }

    /**
     * Returns the handler's priority.
     *
     * @return The priority of the handler.
     */
    public HandlerPriority getPriority() {
        return this.priority;
    }

    /**
     * Returns if the handler will ignore cancelled events.
     *
     * @return The ignore cancelled state.
     */
    public boolean ignoresCanceled() {
        return this.handleCanceled;
    }

    /**
     * Returns the plugin that registered this handler.
     *
     * @return The plugin that registered this handler.
     */
    public Plugin registrar() {
        return this.plugin;
    }

    /**
     * Sets the callback method for when the event is invoked.
     *
     * @param listener An event handler method.
     * @return Method chaining.
     */
    public EventHandler<T> listener(EventConsumer<T> listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Changes the handler's priority in handling events.
     *
     * @param priority The priority of the handler.
     * @return Method chaining.
     */
    public EventHandler<T> priority(HandlerPriority priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Sets if the handler will ignore cancelled events.
     *
     * @param ignore If the handler should ignore cancelled events.
     * @return Method chaining.
     */
    public EventHandler<T> ignore(boolean ignore) {
        this.handleCanceled = ignore;
        return this;
    }

    /** Registers the handler into the PluginManager. */
    public void register(Plugin plugin) {
        this.plugin = plugin;
        Grasscutter.getPluginManager().registerListener(this);
    }
}
