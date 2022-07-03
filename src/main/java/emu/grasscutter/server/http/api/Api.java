package emu.grasscutter.server.http.api;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.http.Router;
import emu.grasscutter.command.CommandMap;
import express.Express;
import io.javalin.Javalin;
import static emu.grasscutter.utils.Language.translate;
import emu.grasscutter.utils.ConfigContainer;

public final class Api implements Router {
    @Override public void applyRoutes(Express express, Javalin handle) {
        express.get("/api/check_status", (request, response) -> response.send("OK"));
        express.all("/api/do_command", (request, response) -> {
            if(Grasscutter.config.httpapi.UseApi.contains("true")){
                if(Grasscutter.config.httpapi.UseApiKey.contains("true")){
                    String key = request.query("key");
                    String rawBodyData = request.ctx().body();
                    if(key.equals(Grasscutter.config.httpapi.ApiKey)){
                        response.send(CommandMap.getInstance().invoke(null,null,rawBodyData));
                    }
                    else{
                        response.send(translate("messages.status.httpapi_key_error"));
                    }
                }
                else{
                    String rawBodyData = request.ctx().body();
                    response.send(CommandMap.getInstance().invoke(null,null,rawBodyData));
                }
            }
            else {
                response.send(translate("messages.status.httpapi_do_not_use"));
            }
        });
    }
}
