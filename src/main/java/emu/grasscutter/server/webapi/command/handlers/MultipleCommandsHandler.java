package emu.grasscutter.server.webapi.command.handlers;

import emu.grasscutter.command.CommandMap;
import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentInfo;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.command.handlers.interfaces.CommandRequestHandler;
import emu.grasscutter.server.webapi.requestdata.CommandRequestData;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import io.javalin.http.Context;

import java.util.List;


@RequestRoute(routes = "commands")
public class MultipleCommandsHandler implements CommandRequestHandler {

    @Override
    public void invoke(CommandRequestData data, Context context) {
        if(!data.getData().containsKey("commands")) {
            ResponseBuilder.buildArgumentMissing("Argument is missing", getArgumentParser());
        }

        var commands = data.getData().get("commands");
        if(commands instanceof List<?>) {
            for(var cmdObj : (List<?>)commands) {
                if(cmdObj instanceof String command) {
                    CommandMap.getInstance().invoke(data.getExecutor(), data.getTarget(), command);
                }
            }
        }
    }

    @Override
    public ArgumentParser getArgumentParser() {
        ArgumentParser parser = new ArgumentParser();
        ArgumentInfo commandsArgInfo = new ArgumentInfo("commands", "string[]");
        commandsArgInfo.setDescription("要执行的命令");
        parser.addArgument(commandsArgInfo);
        return parser;
    }
}
