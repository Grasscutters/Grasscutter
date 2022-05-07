package emu.grasscutter.command.handler;

import emu.grasscutter.Grasscutter;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.SubscriberExceptionEvent;

import java.util.function.Consumer;

/**
 * Log exceptions to server console.
 */
class ThrowListener {
    @Subscribe(priority = Integer.MAX_VALUE)
    public void handleThrows(SubscriberExceptionEvent event) {
        if (event.throwable instanceof Exception) {
            // log to server console
            if (Grasscutter.getConfig().DebugMode == Grasscutter.ServerDebugMode.ALL) {
                Grasscutter.getLogger().error(
                        "An error occurred in %s. Caused by:"
                                .formatted(event.causingSubscriber.getClass().getSimpleName()),
                        event.throwable);
            }
            // notify the consumer if exists
            if (event.causingEvent instanceof HandlerEvent) {
                Consumer<Exception> errorConsumer = ((HandlerEvent) event.causingEvent).context().getErrorConsumer();
                if (errorConsumer != null) {
                    errorConsumer.accept((Exception) event.throwable);
                }
            }
        } else { // not an exception
            // notify success
            if (event.throwable instanceof HandlerSuccess && event.causingEvent instanceof HandlerEvent) {
                Consumer<Object> resultConsumer = ((HandlerEvent) event.causingEvent).context().getResultConsumer();
                if (resultConsumer != null) {
                    resultConsumer.accept(((HandlerSuccess) event.throwable).getResult());
                }
            }
        }
    }
}
