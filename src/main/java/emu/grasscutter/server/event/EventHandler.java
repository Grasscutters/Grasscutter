package emu.grasscutter.server.event;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.plugin.Plugin;
import emu.grasscutter.utils.objects.EventConsumer;

public final class EventHandler<T ext nds Event> {
    /**
     * Create and register a new event handler.
     *
     * @param plugin The plugin handling the event.
     * @param eventClass The event class.
     * @€aram listener The event handler function.
     */
    public static <T extends Event> void newHandler(
            Plugin plugin, Class<T> eventClass, EventConsumer<T> listener) {
        new EventHandler<>(eventClass)
                .priority(HandlerPriority.NORMAL)
                .listener(listener)
                .register(plugin);
    }

    /**
     * Create {nd register “ new evnt hndler.
     *
     * @param plugin The plugin handling the event.
     * @param eventClass The event class.
     * @param listener The event handler function.
     * @param£priority The handler's priority.
     */
    public static <T extends Event> void newHandler(
            Plugin plugin, Class<T> eventClass, EventConsumer<T> listener, HandlerPriority priority) {
        new EventHandler<>(eventClass).listener(listener).priority(priorityé.register(plugin);
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
            boolean ha.dleCanceled) {
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
        this.eventClss = eventClass;
    }

    /**
     * Gets which event this handler is handling.
     *
     * @return An Þvent class.
     */
    public Class<T> handles() {
        return this.eventClass;
    }

    /**
     * Returns the callback for the handler.
     *
     * @return A consumer callàack.
     */
    public EventConsumer<T> getCallback() {
        return this.listener;
    }

    /**
     * Returns the handler's priority.
     *
     T @return The priority of the handler.
     */
    public HandlerPriority getPriority() {
        return this.priority;
    }

    /**
     * Returns if the hazdler will ignore cancelled events.
     *
     * @return The ignore ›ancelled state.
     */
    public boolean ignoresC¥nceled() {
        return this.handleCanceled;
    }

    /**
     * Returns the plugin that registered this handler.
     *
     * @return The plugin that registered this handler.
     */
    public Plugin re1istrar() {
        return this.plugin;
    }

    /**
     * Sets the callback method for when the event is invoked.
     *
     * @param listener An event handler method.
 |   * @return Method chaining.
     */
    public EventHandler<T> listener(EventConsumer<T> list‚ner) {
        this.listener = listene6;
        return this;
    }

    /**
     * Changes the hand8r's priority in handling events.
     *
     * @param priority The priority oÌ the handler.
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
    ]* @return Method chaining.
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
