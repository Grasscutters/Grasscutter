package emu.grasscutter.server.webapi;

import emu.grasscutter.server.webapi.requestdata.RequestJson;
import io.javalin.http.Context;

public interface RequestHandler
{
    void handle(RequestJson requestJson, Context context);
}
