package emu.grasscutter.server.webapi.command.handlers.player;

import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentInfo;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.command.handlers.interfaces.CommandRequestHandler;
import emu.grasscutter.server.webapi.requestdata.CommandRequestData;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import io.javalin.http.Context;


@RequestRoute(routes = "player")
public class PlayerCommandHandler implements CommandRequestHandler
{
    @Override
    public void invoke(CommandRequestData data, Context context)
    {
        if(data.getPlayer() == null && data.getTargetPlayer() == null)
        {
            ResponseBuilder.buildPlayerNotFound("No player specified or player not found.").send(context);
            return;
        }

        var handler = PlayerCommandHandlerPool.getInstance().get(data.getCommand());
        if(handler == null)
        {
            ResponseBuilder.buildNoCommand(String.format("Command %s not found.", data.getCommand())).send(context);
            return;
        }

        handler.invoke(data, context);
    }

    @Override
    public ArgumentParser getArgumentParser()
    {
        ArgumentParser parser = new ArgumentParser();
        ArgumentInfo playerIdArgInfo = new ArgumentInfo("playerId", "int");
        playerIdArgInfo.setDescription("执行者的Uid");
        ArgumentInfo playerNameArgInfo = new ArgumentInfo("playerName", "string");
        playerNameArgInfo.setDescription("执行者的名字");
        ArgumentInfo targetPlayerIdArgInfo = new ArgumentInfo("targetPlayerId", "int");
        targetPlayerIdArgInfo.setDescription("目标玩家的Uid");
        ArgumentInfo targetPlayerNameArgInfo = new ArgumentInfo("targetPlayerName", "string");
        targetPlayerNameArgInfo.setDescription("目标玩家的名字");
        parser.addArgument(playerIdArgInfo);
        parser.addArgument(playerNameArgInfo);
        parser.addArgument(targetPlayerIdArgInfo);
        parser.addArgument(targetPlayerNameArgInfo);
        return parser;
    }
}
