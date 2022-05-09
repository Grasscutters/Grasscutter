package emu.grasscutter.command.handler.collection;

import emu.grasscutter.command.handler.*;
import emu.grasscutter.command.handler.annotation.Handler;
import emu.grasscutter.command.handler.annotation.HandlerCollection;
import lombok.SneakyThrows;

import static emu.grasscutter.command.handler.ContextNaming.*;
@HandlerCollection
public class TestHandlerCollection {
    @Handler(ECHO)
    @SneakyThrows
    public void echo(HandlerContext context) {
        int sender = context.getOptional(SenderUid, 0);
        int target = context.getOptional(TargetUid, 0);

        // This will take a long time before echo
        for (int i = 0; i < 3; i++) {
            context.notify("tick");
            Thread.sleep(1000);
            context.errorAndContinue(new RuntimeException("tock"));
            Thread.sleep(1000);
        }
        context.notify("%d, %d".formatted(sender, target));
        context.notify(context.getRequired("content", String.class));

        context.returnWith("Finished Now.");

        // the following code will not be executed.
        context.notify("some word.");
    }

    public static final String ECHO = "test.echo";
}
