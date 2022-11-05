package emu.grasscutter.server.webapi.player;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.server.webapi.RequestDispatcher;
import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentInfo;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandler;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandlerPool;
import emu.grasscutter.server.webapi.requestdata.PlayerInfoRequestData;
import emu.grasscutter.server.webapi.requestdata.RequestJson;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import emu.grasscutter.utils.Language;
import io.javalin.http.Context;

@RequestRoute(routes = "player")
public class PlayerInfoRequestDispatcher implements RequestDispatcher {
    @Override
    public void dispatch(RequestJson requestJson, Context context) {
        var data = requestJson.getDataAs(PlayerInfoRequestData.class);
        if(data == null || data.getData() == null) {
            ResponseBuilder.buildInvalidRequest("Need additional data.").send(context);
            return;
        }

        data.setRequestJson(requestJson);
        var handler = PlayerInfoRequestHandlerPool.getInstance().get(data.getAttribute());
        if(handler == null) {
            ResponseBuilder.buildAttributeNotFound(data.getAttribute()).send(context);
            return;
        }

        if(!handler.noTarget() && data.getPlayer() == null) {
            ResponseBuilder.buildPlayerNotFound().send(context);
            return;
        }

        try {
            switch (data.getOperation()) {
                case "get" -> getAttribute(handler, data, context);
                case "set" -> setAttribute(handler, data, context);
                case "add" -> addAttribute(handler, data, context);
                case "setHelp" -> attributeSetHelp(handler, data, context);
                case "addHelp" -> attributeAddHelp(handler, data, context);
                default -> ResponseBuilder.buildOperationNotFound(data.getOperation()).send(context);
            }
        }
        catch(Exception e) {
            ResponseBuilder.buildNormalError(Language.translate("webapi.tips.server_error"), null).send(context);
            Grasscutter.getLogger().error(e.toString());
        }
    }

    @Override
    public ArgumentParser getArgumentParser() {
        ArgumentInfo attrArgument = new ArgumentInfo("attr", "string");
        attrArgument.setDescription(Language.translate("webapi.player.args.attr"));

        ArgumentInfo opArgument = new ArgumentInfo("op", "string");
        opArgument.setDescription(Language.translate("webapi.player.args.op"));

        ArgumentInfo dataArgument = new ArgumentInfo("data", "object");
        opArgument.setDescription(Language.translate("webapi.player.args.data"));

        return new ArgumentParser(attrArgument, opArgument, dataArgument);

    }

    void getAttribute(PlayerInfoRequestHandler handler, PlayerInfoRequestData data, Context context) {
        if(!handler.canGet()) {
            ResponseBuilder.buildNotSupportOperation("get", data.getAttribute(), handler).send(context);
            return;
        }

        handler.getAttribute(data, context);
    }

    void setAttribute(PlayerInfoRequestHandler handler, PlayerInfoRequestData data, Context context) {
        if(!handler.canSet()) {
            ResponseBuilder.buildNotSupportOperation("set", data.getAttribute(), handler).send(context);
            return;
        }

        handler.setAttribute(data, context);
    }

    void addAttribute(PlayerInfoRequestHandler handler, PlayerInfoRequestData data, Context context) {
        if(!handler.canAdd()) {
            ResponseBuilder.buildNotSupportOperation("add", data.getAttribute(), handler).send(context);
            return;
        }

        handler.addAttribute(data, context);
    }

    void attributeSetHelp(PlayerInfoRequestHandler handler, PlayerInfoRequestData data, Context context) {
        if(!handler.canSet()) {
            ResponseBuilder.buildNotSupportOperation("set", data.getAttribute(), handler).send(context);
            return;
        }

        var jsonHelp = handler.getSetArgumentParser().getJsonHelp();
        ResponseBuilder.buildOperationSuccess("setHelp", data.getAttribute(), jsonHelp).send(context);
    }

    void attributeAddHelp(PlayerInfoRequestHandler handler, PlayerInfoRequestData data, Context context) {
        if(!handler.canAdd()) {
            ResponseBuilder.buildNotSupportOperation("set", data.getAttribute(), handler).send(context);
            return;
        }

        var jsonHelp = handler.getAddArgumentParser().getJsonHelp();
        ResponseBuilder.buildOperationSuccess("addHelp", data.getAttribute(), jsonHelp).send(context);
    }

}
