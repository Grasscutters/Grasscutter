package emu.grasscutter.command.handler.collections;

import emu.grasscutter.command.handler.*;
import emu.grasscutter.command.handler.annotation.Handler;
import emu.grasscutter.command.handler.annotation.HandlerCollection;
import lombok.SneakyThrows;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@HandlerCollection
public class TestHandlerCollectionCollection {
    @Handler(ECHO)
    @SneakyThrows
    public void echo(HandlerContext context) {
        // This will take a long time before echo
        for (int i = 0; i < 3; i++) {
            context.notify("tick");
            Thread.sleep(1000);
            context.errorAndContinue(new RuntimeException("tock"));
            Thread.sleep(1000);
        }
        context.notify(context.getRequired("content", String.class));
        context.returnWith("Finished Now.");

        // the following code will not be executed.
        context.notify("some word.");
    }

    public static final String ECHO = "test.echo";
}
