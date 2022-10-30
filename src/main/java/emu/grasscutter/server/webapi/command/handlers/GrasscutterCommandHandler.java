package emu.grasscutter.server.webapi.command.handlers;


import emu.grasscutter.command.CommandMap;
import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.command.handlers.interfaces.CommandRequestHandler;
import emu.grasscutter.server.webapi.requestdata.CommandRequestData;
import io.javalin.http.Context;

@RequestRoute(routes = "gccmd")
public class GrasscutterCommandHandler implements CommandRequestHandler
{
    @Override
    public void invoke(CommandRequestData data, Context context)
    {
        CommandMap.getInstance().invoke(data.getPlayer(), data.getTargetPlayer(), data.getCommand());
    }

    @Override
    public ArgumentParser getArgumentParser()
    {
        return new ArgumentParser();
    }
}
