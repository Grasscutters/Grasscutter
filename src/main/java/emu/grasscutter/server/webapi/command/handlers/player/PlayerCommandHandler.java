package emu.grasscutter.server.webapi.command.handlers.player;

import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentInfo;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.command.handlers.interfaces.CommandRequestHandler;
import emu.grasscutter.server.webapi.requestdata.CommandRequestData;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import io.javalin.http.Context;


@RequestRoute(routes = "player")
public class PlayerCommandHandler implements CommandRequestHandler {
    @Override
    public void invoke(CommandRequestData data, Context context) {
        if(data.getExecutor() == null && data.getTarget() == null) {
            ResponseBuilder.buildPlayerNotFound().send(context);
            return;
        }

        String command = data.getData().get("command").toString();
        var handler = PlayerCommandHandlerPool.getInstance().get(command);

        if(handler == null) {
            ResponseBuilder.buildNoPlayerCommand().send(context);
            return;
        }

        handler.invoke(data, context);
    }

    @Override
    public ArgumentParser getArgumentParser() {

        ArgumentInfo playerIdArgInfo = new ArgumentInfo("executorId", "int");
        playerIdArgInfo.setDescription("webapi.command.args.executor_id");
        ArgumentInfo playerNameArgInfo = new ArgumentInfo("executorName", "string");
        playerNameArgInfo.setDescription("webapi.command.args.executor_name");
        ArgumentInfo targetPlayerIdArgInfo = new ArgumentInfo("targetId", "int");
        targetPlayerIdArgInfo.setDescription("webapi.command.args.target_id");
        ArgumentInfo targetPlayerNameArgInfo = new ArgumentInfo("targetName", "string");
        targetPlayerNameArgInfo.setDescription("webapi.command.args.target_name");

        return new ArgumentParser(playerIdArgInfo, playerNameArgInfo, targetPlayerIdArgInfo, targetPlayerNameArgInfo);
    }
}
