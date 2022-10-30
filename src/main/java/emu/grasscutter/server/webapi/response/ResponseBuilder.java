package emu.grasscutter.server.webapi.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.command.CommandRequestHandlerPool;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandler;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandlerPool;
import emu.grasscutter.server.webapi.player.tools.PlayerInfoHandlerTools;
import emu.grasscutter.server.webapi.player.tools.PlayerTools;

public class ResponseBuilder
{
    public static Response buildNormalError(String info, Object data)
    {
        Response response = new Response();
        response.setState("NormalError");
        response.setTextInfo(info);
        response.setAdditionalData(data);
        return response;
    }

    public static Response buildOperationSuccess(String operation, String attribute, Object data)
    {
        Response response = new Response();
        response.setSuccess(true);
        response.setState("Success");
        response.setTextInfo(String.format("%s value of attribute %s successfully", operation, attribute));
        response.setAdditionalData(data);
        return response;
    }

    public static Response buildOperationFailed(String operation, String attribute, Object data)
    {
        Response response = new Response();
        response.setState("Error");
        response.setTextInfo(String.format("Failed to %s value of attribute %s", operation, attribute));
        response.setAdditionalData(data);
        return response;
    }

    public static Response buildInvalidRequest(String info)
    {
        Response response = new Response();
        response.setState("InvalidRequest");
        response.setTextInfo(info);
        return response;
    }

    public static Response buildPlayerNotFound(String info)
    {
        Response response = new Response();
        response.setState("PlayerNotFound");
        response.setTextInfo(info);
        response.setAdditionalData(PlayerTools.getAllOnlinePlayer());
        return response;
    }

    public static Response buildAttributeNotFound(String attribute)
    {
        Response response = new Response();
        response.setState("AttributeNotFound");
        response.setTextInfo("Attribute " + attribute + " not found.");
        JsonObject additionalData = new JsonObject();
        JsonArray availableAttributes = new JsonArray();
        for (var r : PlayerInfoRequestHandlerPool.getInstance().getRoutes())
        {
            availableAttributes.add(r);
        }
        additionalData.add("availableAttributes", availableAttributes);
        response.setAdditionalData(additionalData);
        return response;
    }

    public static Response buildNotSupportOperation(String operation, String attribute, PlayerInfoRequestHandler handler)
    {
        Response response = new Response();
        response.setState("OperationNotSupported");
        response.setTextInfo("Operation " + operation + " is not supported for attribute " + attribute);
        JsonObject data = new JsonObject();
        JsonArray availableOperations = new JsonArray();
        for(var op : PlayerInfoHandlerTools.getAvailableOperations(handler))
        {
            availableOperations.add(op);
        }
        data.add("availableOperations", availableOperations);
        response.setAdditionalData(data);
        return response;
    }

    public static Response buildOperationNotFound(String operation, PlayerInfoRequestHandler handler)
    {
        Response response = new Response();
        response.setState("OperationNotFound");
        response.setTextInfo("Operation " + operation + " is not found.");
        JsonObject data = new JsonObject();
        JsonArray availableOperations = new JsonArray();
        for(var op : new String[]{"get", "set", "add"})
        {
            availableOperations.add(op);
        }
        data.add("availableOperations", availableOperations);
        response.setAdditionalData(data);
        return response;
    }

    public static Response buildArgumentMissing(String info, ArgumentParser parser)
    {
        Response response = new Response();
        response.setState("RequiredArgumentsAreMissing");
        response.setTextInfo(info);
        response.setAdditionalData(parser.getJsonHelp());
        return response;
    }

    public static Response buildNoCommand(String info)
    {
        Response response = new Response();
        response.setState("CommandNotFound");
        JsonObject additionalData = new JsonObject();
        JsonArray availableCommands = new JsonArray();
        response.setTextInfo(info);
        additionalData.add("availableCommands", availableCommands);
        for(var r : CommandRequestHandlerPool.getInstance().getRoutes())
        {
            availableCommands.add(r);
        }

        response.setAdditionalData(additionalData);
        return response;
    }
}
