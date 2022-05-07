package emu.grasscutter.command.handler.builtin;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.handler.HandlerEvent;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;

public class EventLogListener {
    @Subscribe(priority = Integer.MAX_VALUE)
    public void log(HandlerEvent event) {
        Logger logger = Grasscutter.getLogger();
        StringBuilder sb = new StringBuilder();
        sb.append("collection: %d, method: %d\n".formatted(event.getCollectionCode(), event.getMethodCode()));
        sb.append("Context:\n");
        event.getContext().getContent().forEach((k, v) -> sb.append("%s: %s".formatted(k, v.toString())));
        logger.info(sb.toString());
    }
}
