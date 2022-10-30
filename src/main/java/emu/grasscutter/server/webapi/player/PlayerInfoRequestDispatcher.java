package emu.grasscutter.server.webapi.player;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.webapi.RequestDispatcher;
import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandler;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandlerPool;
import emu.grasscutter.server.webapi.requestdata.PlayerInfoRequestData;
import emu.grasscutter.server.webapi.requestdata.RequestJson;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import io.javalin.http.Context;

@RequestRoute(routes = "player")
public class PlayerInfoRequestDispatcher implements RequestDispatcher
{
    @Override
    public void dispatch(RequestJson requestJson, Context context)
    {
        var data = requestJson.getDataAs(PlayerInfoRequestData.class);
        if(data == null)
        {
            ResponseBuilder.buildInvalidRequest("Need additional data.").send(context);
            return;
        }

        data.setRequestJson(requestJson);
        var handler = PlayerInfoRequestHandlerPool.getInstance().get(data.getAttribute());
        if(handler == null)
        {
            ResponseBuilder.buildAttributeNotFound(data.getAttribute()).send(context);
            return;
        }

        if(!handler.noTarget() && data.getPlayer() == null)
        {
            ResponseBuilder.buildPlayerNotFound("No player specified or player not found.").send(context);
            return;
        }

        try
        {
            switch (data.getOperation())
            {
                case "get" -> getAttribute(handler, data, context);
                case "set" -> setAttribute(handler, data, context);
                case "add" -> addAttribute(handler, data, context);
                default -> ResponseBuilder.buildOperationNotFound(data.getOperation(), handler).send(context);
            }
        }
        catch(Exception e)
        {
            ResponseBuilder.buildNormalError("Error when handling the request.", null).send(context);
            Grasscutter.getLogger().error(e.toString());
        }
    }

    @Override
    public ArgumentParser getArgumentParser()
    {
        //TODO: implement it
        return null;
    }

    void getAttribute(PlayerInfoRequestHandler handler, PlayerInfoRequestData data, Context context)
    {
        if(!handler.canGet())
        {
            ResponseBuilder.buildNotSupportOperation("get", data.getAttribute(), handler).send(context);
            return;
        }

        handler.getAttribute(data, context);
    }

    void setAttribute(PlayerInfoRequestHandler handler, PlayerInfoRequestData data, Context context)
    {
        if(!handler.canSet())
        {
            ResponseBuilder.buildNotSupportOperation("set", data.getAttribute(), handler).send(context);
            return;
        }

        handler.setAttribute(data, context);
    }

    void addAttribute(PlayerInfoRequestHandler handler, PlayerInfoRequestData data, Context context)
    {
        if(!handler.canAdd())
        {
            ResponseBuilder.buildNotSupportOperation("add", data.getAttribute(), handler).send(context);
            return;
        }

        handler.addAttribute(data, context);
    }

}
