package emu.grasscutter.command.source;

import emu.grasscutter.command.handler.HandlerContext;
import emu.grasscutter.command.parser.CommandParser;
import emu.grasscutter.command.parser.annotation.Command;
import emu.grasscutter.command.parser.annotation.Origin;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public abstract class BaseCommandSource {

    protected final ConcurrentHashMap<String, Object> persistedContext = new ConcurrentHashMap<>();
    protected BiConsumer<BaseCommandSource, String> registeredCommandConsumer;

    /**
     * Save <code>value</code> to current session.
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key, or <code>null</code> to remove the value.
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    public Object put(@NotNull String key, Object value) {
        if (value != null) {
            return persistedContext.put(key, value);
        }
        return persistedContext.remove(key);
    }

    /**
     * Get a previously stored value by its key.
     * @param key the key whose associated value is to be returned
     * @return the value, or <code>null</code> if the <code>key</code> is not associated with one
     */
    public Object get(@NotNull String key) {
        return persistedContext.get(key);
    }

    public boolean containsKey(@NotNull String key) {
        return persistedContext.containsKey(key);
    }

    public abstract String[] getPermissions();
    public abstract Origin getOrigin();

    /**
     * Display a message to current source.
     */
    public synchronized void info(String message) {
    }

    /**
     * Display an error to current source.
     */
    public synchronized void error(String error) {
    }

    /**
     * <p>Set the prompt string. So the users can know what they are doing.</p>
     * <p>For example in the server console:</p>
     * <blockquote>
     *     <code style="color: green;">> </code><code>target 10000</code><br>
     *     <code style="color: green;">Target: 10000> </code> <code>some command on user 10000</code><br>
     *     <code style="color: green;">Target: 10000> </code> <code>target</code><br>
     *     <code style="color: green;">> </code><code>some other commands</code>
     * </blockquote>
     * <p>This can be useful if you want to develop a shell based on websocket and xterm.js.</p>
     * @param prompt The new prompt value.
     */
    public synchronized void pushPrompt(@NotNull String prompt) {
    }

    /**
     * Restore the prompt to the previous one.
     */
    public synchronized void popPrompt() {
    }

    /**
     * <p>Before the {@link emu.grasscutter.command.handler.HandlerEvent} is dispatched, you can
     * inspect and modify its context. You may set something like sender and target to it.</p>
     * @param contextBuilder the builder to build the handler context
     * @return the final handler context
     */
    public HandlerContext buildContext(HandlerContext.HandlerContextBuilder contextBuilder) {
        return contextBuilder.build();
    }

    /**
     * Register a consumer to process what user inputs in the next line. When user types
     * and presses enter, the whole line will be passed to the consumer along with this
     * command source instead of parsing it as a command.
     * @param consumer a consumer to process the next line
     */
    public void registerCommandConsumer(BiConsumer<BaseCommandSource, String> consumer) {
        registeredCommandConsumer = consumer;
    }

    /**
     * Parse and run the command.
     * @param cmdString user input
     */
    public void runCommand(String cmdString) {
        BiConsumer<BaseCommandSource, String> consumer =
                registeredCommandConsumer != null ? registeredCommandConsumer : CommandParser::run;
        registeredCommandConsumer = null; // restore
        consumer.accept(this, cmdString);
    }
}
