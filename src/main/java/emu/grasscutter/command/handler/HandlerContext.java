package emu.grasscutter.command.handler;

import emu.grasscutter.command.BaseContext;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Getter
public final class HandlerContext extends BaseContext {
    @Nullable
    private final Consumer<Object> messageConsumer;
    @Nullable
    private final Consumer<Object> resultConsumer;
    @Nullable
    private final Consumer<Throwable> errorConsumer;

    @Builder(toBuilder = true)
    public HandlerContext(
            @Singular("content") Map<String, Object> contents,
            @Nullable Consumer<Object> messageConsumer,
            @Nullable Consumer<Object> resultConsumer,
            @Nullable Consumer<Throwable> errorConsumer
    ) {
        this.contents = new ConcurrentHashMap<>(contents);
        this.messageConsumer = messageConsumer;
        this.resultConsumer = resultConsumer;
        this.errorConsumer = errorConsumer;
    }

    /**
     * <p>Report an error to the callback. The following code will be executed.</p>
     * <p>You do <b>NOT</b> need this before an exception is thrown. It will be called automatically.</p>
     * @param throwable a throwable
     */
    public void error(Throwable throwable) {
        if (errorConsumer != null) {
            errorConsumer.accept(throwable);
        }
    }

    /**
     * Send a message to messageConsumer. This may be useful when reporting progresses.
     */
    public void notify(Object message) {
        if (messageConsumer != null) {
            messageConsumer.accept(message);
        }
    }
}
