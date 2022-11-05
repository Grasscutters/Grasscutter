package emu.grasscutter.server.webapi.player.handlers.interfaces;

import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.requestdata.PlayerInfoRequestData;
import io.javalin.http.Context;

public interface PlayerInfoRequestHandler {
    void getAttribute(PlayerInfoRequestData requestData, Context context);
    void setAttribute(PlayerInfoRequestData requestData, Context context);
    void addAttribute(PlayerInfoRequestData requestData, Context context);
    boolean canGet();
    boolean canSet();
    boolean canAdd();
    ArgumentParser getGetArgumentParser();
    ArgumentParser getSetArgumentParser();
    ArgumentParser getAddArgumentParser();
    boolean noTarget();
}
