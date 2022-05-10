package emu.grasscutter.command.handler;

import emu.grasscutter.Grasscutter;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.SubscriberExceptionEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

/**
 * Log exceptions to server console.
 */
public class ThrowListener {
    @Subscribe
    public void handleThrows(SubscriberExceptionEvent event) {
        // log to server console
        if (Grasscutter.getConfig().DebugMode == Grasscutter.ServerDebugMode.ALL) {
            Grasscutter.getLogger().error(
                    "An error occurred in %s. Caused by:"
                            .formatted(event.causingSubscriber.getClass().getSimpleName()),
                    event.throwable);
        }
        // notify the consumer if exists
        if (event.causingEvent instanceof HandlerEvent) {
            Consumer<Throwable> errorConsumer = ((HandlerEvent) event.causingEvent).context().getErrorConsumer();
            if (errorConsumer != null) {
                errorConsumer.accept(event.throwable);
            }
        }
    }
}
