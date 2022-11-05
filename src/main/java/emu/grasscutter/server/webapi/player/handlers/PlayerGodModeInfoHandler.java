package emu.grasscutter.server.webapi.player.handlers;

import com.google.gson.JsonObject;
import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentInfo;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandler;
import emu.grasscutter.server.webapi.requestdata.PlayerInfoRequestData;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import emu.grasscutter.utils.Language;
import io.javalin.http.Context;

@RequestRoute(routes = "godMode")
public class PlayerGodModeInfoHandler implements PlayerInfoRequestHandler {
    @Override
    public void getAttribute(PlayerInfoRequestData requestData, Context context) {
        try {
            JsonObject data = new JsonObject();
            data.addProperty("enabled", requestData.getPlayer().inGodmode());
            ResponseBuilder.buildOperationSuccess("get", "godMode", data).send(context);
        }
        catch(Exception e) {
            ResponseBuilder.buildOperationFailed("get", "godMode", null).send(context);
        }

    }

    @Override
    public void setAttribute(PlayerInfoRequestData requestData, Context context) {
        try {
            var argParser = getSetArgumentParser();
            var args = argParser.parseJson(requestData.getData());

            JsonObject data = new JsonObject();
            data.addProperty("oldValue", requestData.getPlayer().inGodmode());

            boolean enabled = args.get("enabled").getAsBoolean();
            requestData.getPlayer().setGodmode(enabled);
            data.addProperty("value", requestData.getPlayer().inGodmode());
            ResponseBuilder.buildOperationSuccess("set", "godMode", data).send(context);
        }
        catch(Exception e) {
            ResponseBuilder.buildOperationFailed("set", "godMode", null).send(context);
        }
    }

    @Override
    public void addAttribute(PlayerInfoRequestData requestData, Context context) {
    }

    @Override
    public boolean canGet() {
        return true;
    }

    @Override
    public boolean canSet() {
        return true;
    }

    @Override
    public boolean canAdd() {
        return false;
    }

    @Override
    public ArgumentParser getGetArgumentParser() {
        return new ArgumentParser();
    }

    @Override
    public ArgumentParser getSetArgumentParser() {
        ArgumentInfo enabledArgInfo = new ArgumentInfo("enabled", "bool");
        enabledArgInfo.setDescription(Language.translate("webapi.player.commands.god_mode.args.enabled"));
        enabledArgInfo.setDefaultValue("false");
        return new ArgumentParser(enabledArgInfo);
    }

    @Override
    public ArgumentParser getAddArgumentParser() {
        return null;
    }

    @Override
    public boolean noTarget() {
        return false;
    }
}
