package emu.grasscutter.server.webapi.command.handlers;


import emu.grasscutter.command.CommandMap;
import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentInfo;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.command.handlers.interfaces.CommandRequestHandler;
import emu.grasscutter.server.webapi.requestdata.CommandRequestData;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import io.javalin.http.Context;

@RequestRoute(routes = "gccmd")
public class GrasscutterCommandHandler implements CommandRequestHandler {
    @Override
    public void invoke(CommandRequestData data, Context context) {
        Object commandObj = data.getData().get("command");

        if(commandObj == null) {
            ResponseBuilder.buildArgumentMissing("Argument is missing", getArgumentParser()).send(context);
            return;
        }
        String command = commandObj.toString();
        CommandMap.getInstance().invoke(data.getExecutor(), data.getTarget(), command);
        ResponseBuilder.buildCommandSuccess().send(context);
    }

    @Override
    public ArgumentParser getArgumentParser() {
        ArgumentInfo commandArg =
            new ArgumentInfo("command", "string","Command to execute");

        return new ArgumentParser(commandArg);
    }
}
