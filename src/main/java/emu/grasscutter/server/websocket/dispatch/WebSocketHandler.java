package emu.grasscutter.server.websocket.dispatch;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.server.http.dispatch.RegionHandler;
import emu.grasscutter.server.websocket.dispatch.DispatchMessageHandler;
import express.Express;
import io.javalin.Javalin;

import java.util.Arrays;
import java.util.HashMap;

import static emu.grasscutter.Configuration.*;

public final class WebSocketHandler implements Router {
    public static HashMap<String,Region> regionsIp = new HashMap<>();
    @Override
    public void applyRoutes(Express express, Javalin handle) {
        if (SERVER.runMode == Grasscutter.ServerRunMode.HYBRID){
            regionsIp.put("server",SERVER.dispatch.regions[0]);
        }
        express.ws("/websocket",wsHandler -> {
            wsHandler.onConnect(new DispatchConnectHandler());
            wsHandler.onMessage(new DispatchMessageHandler());
            wsHandler.onClose(new DispatchCloseHandler());
        });
    }
    public static void reinitialize(){
        SERVER.dispatch.regions = Arrays.copyOf(regionsIp.values().toArray(), regionsIp.values().toArray().length, Region[].class);
        RegionHandler.initialize();
    }
}
