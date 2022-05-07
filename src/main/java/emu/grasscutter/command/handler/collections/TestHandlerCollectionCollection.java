package emu.grasscutter.command.handler.collections;

import emu.grasscutter.command.handler.*;
import emu.grasscutter.command.handler.annotation.HandlerCollection;
import lombok.SneakyThrows;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@HandlerCollection(collectionCode = HandlerCollectionCode.Test, collectionName = "test")
public class TestHandlerCollectionCollection extends BaseHandlerCollection {

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    @SneakyThrows
    public void echo(HandlerEvent event) {
        if (notValid(event, Echo)) {
            return;
        }
        HandlerContext context = event.getContext();

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

    public static final int Echo = 0;
}
