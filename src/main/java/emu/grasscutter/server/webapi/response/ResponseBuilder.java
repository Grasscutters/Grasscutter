package emu.grasscutter.server.webapi.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.command.CommandRequestHandlerPool;
import emu.grasscutter.server.webapi.command.handlers.player.PlayerCommandHandlerPool;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandler;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandlerPool;
import emu.grasscutter.server.webapi.player.tools.PlayerInfoHandlerTools;
import emu.grasscutter.server.webapi.player.tools.PlayerTools;
import emu.grasscutter.utils.Language;

public class ResponseBuilder {
    public static Response buildNormalError(String info, Object data) {
        Response response = new Response();
        response.retCode = 1;
        response.setStatusCode(502);
        response.setTextInfo(info);
        response.setData(data);
        return response;
    }

    public static Response buildOperationSuccess(String operation, String attribute, Object data) {
        Response response = new Response();
        response.setSuccess(true);
        response.retCode = 0;
        String opTrans = Language.translate("webapi.tips.op_" + operation);
        response.setTextInfo(Language.translate("webapi.tips.op_success", opTrans, attribute));
        response.setData(data);
        return response;
    }

    public static Response buildOperationFailed(String operation, String attribute, Object data) {
        Response response = new Response();
        response.retCode = 2;
        String opTrans = Language.translate("webapi.tips.op_" + operation);
        response.setTextInfo(Language.translate("webapi.tips.op_failed", opTrans, attribute));
        response.setData(data);
        return response;
    }

    public static Response buildInvalidRequest(String info) {
        Response response = new Response();
        response.setStatusCode(400);
        response.retCode = 3;
        response.setTextInfo(info);
        return response;
    }

    public static Response buildPlayerNotFound() {
        Response response = new Response();
        response.retCode = 4;
        response.setTextInfo(Language.translate("webapi.tips.no_player"));
        response.setData(PlayerTools.getAllOnlinePlayer());
        return response;
    }

    public static Response buildAttributeNotFound(String attribute) {
        Response response = new Response();
        response.retCode = 5;
        response.setTextInfo(Language.translate("webapi.tips.no_attr", attribute));
        JsonObject additionalData = new JsonObject();
        JsonArray availableAttributes = new JsonArray();
        for (var r : PlayerInfoRequestHandlerPool.getInstance().getRoutes()) {
            availableAttributes.add(r);
        }
        additionalData.add("availableAttributes", availableAttributes);
        response.setData(additionalData);
        return response;
    }

    public static Response buildNotSupportOperation(String operation, String attribute, PlayerInfoRequestHandler handler) {
        Response response = new Response();
        response.retCode = 6;
        response.setTextInfo(Language.translate("webapi.tips.not_support_op", attribute, operation));
        JsonObject data = new JsonObject();
        JsonArray availableOperations = new JsonArray();
        for(var op : PlayerInfoHandlerTools.getAvailableOperations(handler)) {
            availableOperations.add(op);
        }
        data.add("availableOperations", availableOperations);
        response.setData(data);
        return response;
    }

    public static Response buildOperationNotFound(String operation) {
        Response response = new Response();
        response.setTextInfo(Language.translate("webapi.tips.no_op", operation));
        response.retCode = 7;
        response.setTextInfo("Operation " + operation + " is not found.");
        JsonObject data = new JsonObject();
        JsonArray availableOperations = new JsonArray();
        for(var op : new String[]{"get", "set", "add"}) {
            availableOperations.add(op);
        }
        data.add("availableOperations", availableOperations);
        response.setData(data);
        return response;
    }


    public static Response buildInvalidArgument(ArgumentParser parser) {
        Response response = new Response();
        response.setStatusCode(400);
        response.retCode = 8;
        response.setTextInfo(Language.translate("webapi.tips.invalid_argument"));
        response.setData(parser.getJsonHelp());
        return response;
    }

    public static Response buildArgumentMissing(String info, ArgumentParser parser) {
        Response response = new Response();
        response.setStatusCode(400);
        response.retCode = 9;
        response.setTextInfo(info);
        response.setData(parser.getJsonHelp());
        return response;
    }

    public static Response buildNoPlayerCommand() {
        Response response = new Response();

        response.retCode = 10;
        JsonObject additionalData = new JsonObject();
        JsonArray availableCommands = new JsonArray();
        response.setTextInfo(Language.translate("webapi.tips.no_command"));
        additionalData.add("availableCommands", availableCommands);
        for(var r : PlayerCommandHandlerPool.getInstance().getRoutes()) {
            availableCommands.add(r);
        }

        response.setData(additionalData);
        return response;
    }

    public static Response buildNoCommandType() {
        Response response = new Response();
        response.retCode = 11;
        JsonObject data = new JsonObject();
        JsonArray availableCommandTypes = new JsonArray();
        response.setTextInfo(Language.translate("webapi.tips.no_command_type"));
        data.add("availableCommandTypes", availableCommandTypes);
        for(var r : CommandRequestHandlerPool.getInstance().getRoutes()) {
            availableCommandTypes.add(r);
        }

        response.setData(data);
        return response;
    }

    public static Response buildCommandSuccess() {
        Response response = new Response();
        response.setTextInfo(Language.translate("webapi.tips.command_success"));
        response.setSuccess(true);
        response.setRetCode(0);
        return response;
    }

}
