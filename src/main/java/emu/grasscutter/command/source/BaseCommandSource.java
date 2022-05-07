package emu.grasscutter.command.source;

import emu.grasscutter.command.handler.HandlerContext;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public abstract class BaseCommandSource {

    protected final ConcurrentHashMap<String, Object> persistedContext = new ConcurrentHashMap<>();

    public void put(String key, Object value) {
        persistedContext.put(key, value);
    }

    public Object get(String key) {
        return persistedContext.get(key);
    }

    public abstract String getPermission();

    @Setter
    protected BiConsumer<BaseCommandSource, String> registeredCommandConsumer;
    /**
     * You can add other parameters to the context before it is sent to dispatcher.
     */
    public void beforeHandlerDispatch(HandlerContext.HandlerContextBuilder contextBuilder) {
    }

    /**
     * Display a message to current source.
     */
    public synchronized void info(Object message) {
    }

    /**
     * Display an error to current source.
     */
    public synchronized void error(Throwable error) {
    }

    /**
     * <p>Set or restore the prompt string. So the users can know what they are doing.</p>
     * <p>For example in the server console:</p>
     * <blockquote>
     *     <code style="color: green;">> </code><code>target 10000</code><br>
     *     <code style="color: green;">Target: 10000> </code> <code>some command on user 10000</code><br>
     *     <code style="color: green;">Target: 10000> </code> <code>target</code><br>
     *     <code style="color: green;">> </code><code>some other commands</code>
     * </blockquote>
     * @param prompt The new prompt value, or <code>null</code> if you want to restore to default.
     */
    public synchronized void changePrompt(String prompt) {
    }

    public void runCommand(String cmdString) {
        if (registeredCommandConsumer != null) {
            BiConsumer<BaseCommandSource, String> consumer = registeredCommandConsumer;
            registeredCommandConsumer = null;
            consumer.accept(this, cmdString);
        } else {

        }
    }

}
