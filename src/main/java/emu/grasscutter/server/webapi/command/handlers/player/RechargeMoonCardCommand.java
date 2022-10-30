package emu.grasscutter.server.webapi.command.handlers.player;

import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.command.handlers.interfaces.CommandRequestHandler;
import emu.grasscutter.server.webapi.requestdata.CommandRequestData;
import io.javalin.http.Context;

@RequestRoute(routes = "rechargemooncard")
public class RechargeMoonCardCommand implements CommandRequestHandler
{
    @Override
    public void invoke(CommandRequestData data, Context context)
    {
        data.getTargetPlayer().rechargeMoonCard();
    }

    @Override
    public ArgumentParser getArgumentParser()
    {
        return new ArgumentParser();
    }
}
