package emu.grasscutter.server.webapi;

import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.requestdata.RequestJson;
import io.javalin.http.Context;

public interface RequestDispatcher
{
    void dispatch(RequestJson requestJson, Context context);
    ArgumentParser getArgumentParser();
}
