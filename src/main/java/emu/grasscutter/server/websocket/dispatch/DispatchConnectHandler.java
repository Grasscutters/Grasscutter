package emu.grasscutter.server.websocket.dispatch;

import emu.grasscutter.Grasscutter;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import org.jetbrains.annotations.NotNull;

import static emu.grasscutter.Configuration.SERVER;

public class DispatchConnectHandler implements WsConnectHandler {
    @Override
    public void handleConnect(@NotNull WsConnectContext wsConnectContext) throws Exception {
        String key = wsConnectContext.queryParam("key");
        if(!key.equals(SERVER.dispatch.key)){
            Grasscutter.getLogger().error("Invalid key for websocket connection! IP : "+wsConnectContext.session.getRemoteAddress().getAddress().toString());
            wsConnectContext.session.close();
        }
    }
}
