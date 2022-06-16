package emu.grasscutter.server.websocket.dispatch;

import emu.grasscutter.Grasscutter;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import org.jetbrains.annotations.NotNull;
import static emu.grasscutter.server.websocket.dispatch.WebSocketHandler.regionsIp;
import static emu.grasscutter.server.websocket.dispatch.WebSocketHandler.reinitialize;

public class DispatchCloseHandler implements WsCloseHandler {
    @Override
    public void handleClose(@NotNull WsCloseContext wsCloseContext) throws Exception {
        Grasscutter.getLogger().info("Websocket with Game Server : " + wsCloseContext.session.getRemoteAddress().getAddress().toString() + " closed, Removing from game server list.");
        regionsIp.remove(wsCloseContext.session.getRemoteAddress().getAddress().toString());
        reinitialize();
    }
}
