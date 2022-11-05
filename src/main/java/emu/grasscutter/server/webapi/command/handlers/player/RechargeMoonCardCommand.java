package emu.grasscutter.server.webapi.command.handlers.player;

import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.command.handlers.interfaces.CommandRequestHandler;
import emu.grasscutter.server.webapi.requestdata.CommandRequestData;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import io.javalin.http.Context;

@RequestRoute(routes = "rechargeMoonCard")
public class RechargeMoonCardCommand implements CommandRequestHandler {
    @Override
    public void invoke(CommandRequestData data, Context context) {
        data.getTarget().rechargeMoonCard();
        ResponseBuilder.buildCommandSuccess().send(context);
    }

    @Override
    public ArgumentParser getArgumentParser() {
        return new ArgumentParser();
    }
}
