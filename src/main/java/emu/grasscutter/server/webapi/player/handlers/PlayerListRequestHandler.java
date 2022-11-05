package emu.grasscutter.server.webapi.player.handlers;


import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandler;
import emu.grasscutter.server.webapi.player.tools.PlayerTools;
import emu.grasscutter.server.webapi.requestdata.PlayerInfoRequestData;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import io.javalin.http.Context;

@RequestRoute(routes = "list")
public class PlayerListRequestHandler implements PlayerInfoRequestHandler {
    @Override
    public void getAttribute(PlayerInfoRequestData requestData, Context context) {
        ResponseBuilder.buildOperationSuccess("get", "list", PlayerTools.getAllOnlinePlayer()).send(context);
    }

    @Override
    public void setAttribute(PlayerInfoRequestData requestData, Context context) {
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
        return false;
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
        return null;
    }

    @Override
    public ArgumentParser getAddArgumentParser() {
        return null;
    }

    @Override
    public boolean noTarget() {
        return true;
    }
}
