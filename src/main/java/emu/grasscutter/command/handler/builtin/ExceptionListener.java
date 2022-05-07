package emu.grasscutter.command.handler.builtin;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.handler.HandlerEvent;
import emu.grasscutter.command.handler.HandlerSuccess;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.SubscriberExceptionEvent;

import java.util.function.Consumer;

/**
 * Log exceptions to server console.
 */
public class ExceptionListener {
    @Subscribe(priority = Integer.MAX_VALUE)
    public void logExceptionToConsole(SubscriberExceptionEvent event) {
        // ignore successes
        if (event.throwable instanceof HandlerSuccess) {
            return;
        }
        // log to server console
        Grasscutter.getLogger().error(
                "An error occurred in %s. Caused by:"
                        .formatted(event.causingSubscriber.getClass().getSimpleName()),
                event.throwable);
        // notify the consumer if exists
        if (event.causingEvent instanceof HandlerEvent) {
            Consumer<Throwable> errorConsumer = ((HandlerEvent) event.causingEvent).getContext().getErrorConsumer();
            if (errorConsumer != null) {
                errorConsumer.accept(event.throwable);
            }
        }
    }
}
