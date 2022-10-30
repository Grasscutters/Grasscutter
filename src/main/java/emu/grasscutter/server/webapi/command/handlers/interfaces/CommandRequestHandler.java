package emu.grasscutter.server.webapi.command.handlers.interfaces;

import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.requestdata.CommandRequestData;
import io.javalin.http.Context;

public interface CommandRequestHandler
{
    void invoke(CommandRequestData data, Context context);
    ArgumentParser getArgumentParser();
}
