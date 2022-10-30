package emu.grasscutter.server.webapi.player.handlers;

import com.google.gson.JsonObject;
import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentInfo;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandler;
import emu.grasscutter.server.webapi.requestdata.PlayerInfoRequestData;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import io.javalin.http.Context;

@RequestRoute(routes = "godmode")
public class PlayerGodModeInfoHandler implements PlayerInfoRequestHandler
{
    @Override
    public void getAttribute(PlayerInfoRequestData requestData, Context context)
    {
        try
        {
            JsonObject data = new JsonObject();
            data.addProperty("enabled", requestData.getPlayer().inGodmode());
            ResponseBuilder.buildOperationSuccess("get", "godmode", data).send(context);
        }
        catch(Exception e)
        {
            ResponseBuilder.buildOperationFailed("get", "godmode", null).send(context);
        }

    }

    @Override
    public void setAttribute(PlayerInfoRequestData requestData, Context context)
    {
        try
        {
            var argParser = getSetArgumentParser();
            var args = argParser.parse(requestData.getRequestJson().getData());

            JsonObject data = new JsonObject();
            data.addProperty("oldValue", requestData.getPlayer().inGodmode());

            if(argParser.isDefaultValue("enabled"))
            {
                data.addProperty("value", requestData.getPlayer().inGodmode());
                ResponseBuilder.buildOperationSuccess("set", "godmode", data).send(context);
                return;
            }
            boolean enabled = Boolean.parseBoolean(args.get("enabled"));
            requestData.getPlayer().setGodmode(enabled);
            data.addProperty("value", requestData.getPlayer().inGodmode());
            ResponseBuilder.buildOperationSuccess("set", "godmode", data).send(context);
        }
        catch(Exception e)
        {
            ResponseBuilder.buildOperationFailed("set", "godmode", null).send(context);
        }
    }

    @Override
    public void addAttribute(PlayerInfoRequestData requestData, Context context)
    {
    }

    @Override
    public boolean canGet()
    {
        return true;
    }

    @Override
    public boolean canSet()
    {
        return true;
    }

    @Override
    public boolean canAdd()
    {
        return false;
    }

    @Override
    public ArgumentParser getGetArgumentParser()
    {
        return new ArgumentParser();
    }

    @Override
    public ArgumentParser getSetArgumentParser()
    {
        ArgumentParser parser = new ArgumentParser();
        ArgumentInfo enabledArgInfo = new ArgumentInfo("enabled", "bool");
        enabledArgInfo.setDescription("是否开启无敌");
        enabledArgInfo.setDefaultValue("~");
        parser.addArgument(enabledArgInfo);
        return parser;
    }

    @Override
    public ArgumentParser getAddArgumentParser()
    {
        return null;
    }

    @Override
    public boolean noTarget()
    {
        return false;
    }
}
