package emu.grasscutter.server.webapi;

import emu.grasscutter.server.http.Router;
import io.javalin.Javalin;
import io.javalin.http.HandlerType;

public class WebApiRouter implements Router {
    @Override
    public void applyRoutes(Javalin javalin) {
        javalin.addHandler(HandlerType.POST, "/server/api", new WebApiHandler());
    }
}
