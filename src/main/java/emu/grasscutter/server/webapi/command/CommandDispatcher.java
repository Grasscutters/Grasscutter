package emu.grasscutter.server.webapi.command;

import emu.grasscutter.command.CommandMap;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.webapi.RequestDispatcher;
import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.requestdata.CommandRequestData;
import emu.grasscutter.server.webapi.requestdata.RequestJson;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import io.javalin.http.Context;


@RequestRoute(routes = "command,cmd")
public class CommandDispatcher implements RequestDispatcher
{

    @Override
    public void dispatch(RequestJson requestJson, Context context)
    {
        var data = requestJson.getDataAs(CommandRequestData.class);

        if(data == null)
        {
            ResponseBuilder.buildInvalidRequest("Need additional data.").send(context);
            return;
        }

        data.setRequestJson(requestJson);
        Player executePlayer = data.getPlayer(), targetPlayer = data.getTargetPlayer();

        if ("command".equals(data.getCommandType()))
        {
            CommandMap.getInstance().invoke(executePlayer, targetPlayer, data.getCommand());
        }
        else
        {
            dispatchToHandler(data, context);
        }
    }

    @Override
    public ArgumentParser getArgumentParser()
    {
        //TODO: Fill parser with argument info
        return new ArgumentParser();
    }

    void dispatchToHandler(CommandRequestData data, Context context)
    {
        CommandRequestHandlerPool.getInstance().get(data.getCommandType()).invoke(data, context);
    }
}
