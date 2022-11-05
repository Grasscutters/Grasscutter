package emu.grasscutter.server.webapi.player.handlers;

import com.google.gson.JsonObject;
import emu.grasscutter.command.CommandMap;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.webapi.annotations.RequestRoute;
import emu.grasscutter.server.webapi.arguments.ArgumentInfo;
import emu.grasscutter.server.webapi.arguments.ArgumentParser;
import emu.grasscutter.server.webapi.player.handlers.interfaces.PlayerInfoRequestHandler;
import emu.grasscutter.server.webapi.requestdata.PlayerInfoRequestData;
import emu.grasscutter.server.webapi.response.ResponseBuilder;
import emu.grasscutter.utils.Language;
import emu.grasscutter.utils.Position;
import io.javalin.http.Context;


@RequestRoute(routes = "pos,position")
public class PositionInfoRequestHandler implements PlayerInfoRequestHandler {


    @Override
    public void getAttribute(PlayerInfoRequestData requestData, Context context) {
        Player p = requestData.getPlayer();
        if(p == null) {
            ResponseBuilder.buildNormalError("Player not found", null).send(context);
            return;
        }

        var pos = p.getPosition();
        if(pos == null) {
            ResponseBuilder.buildOperationSuccess("get", "pos", null).send(context);
        }

        ResponseBuilder.buildOperationSuccess("get", "pos", pos).send(context);
    }

    @Override
    public void setAttribute(PlayerInfoRequestData requestData, Context context) {
        var setArgParser = getSetArgumentParser();
        var args = setArgParser.parseJson(requestData.getData());
        if(args == null || (!args.containsKey("x") && !args.containsKey("y") && !args.containsKey("z"))) {
            ResponseBuilder.buildArgumentMissing("Ignoring unused arguments is allowed.", getGetArgumentParser()).send(context);
            return;
        }
        var player = requestData.getPlayer();
        var playerPos = player.getPosition();
        float x, y, z;
        int sceneId;

        x = args.get("x").withDefault(playerPos.getX()).valueOrDefault().getAsFloat();

        y = args.get("y").withDefault(playerPos.getY()).valueOrDefault().getAsFloat();

        z = args.get("z").withDefault(playerPos.getZ()).valueOrDefault().getAsFloat();

        sceneId = args.get("sceneId").withDefault(player.getSceneId()).valueOrDefault().getAsInt();


        sendPositionJson(context, player, playerPos, x, y, z, sceneId);

    }

    @Override
    public void addAttribute(PlayerInfoRequestData requestData, Context context) {

        var addArgumentParser = getAddArgumentParser();
        var args = addArgumentParser.parseJson(requestData.getData());
        if(args == null) {
            ResponseBuilder.buildArgumentMissing("Ignoring unused arguments is allowed.", getAddArgumentParser()).send(context);
            return;
        }

        var player = requestData.getPlayer();
        var playerPos = player.getPosition();
        double x = playerPos.getX(), y = playerPos.getY(), z = playerPos.getZ();
        int sceneId;
        x += args.get("x").withDefault(0).valueOrDefault().getAsFloat();

        y += args.get("y").withDefault(0).valueOrDefault().getAsFloat();

        z += args.get("z").withDefault(0).valueOrDefault().getAsFloat();

        sceneId = args.get("sceneId").withDefault(player.getSceneId()).valueOrDefault().getAsInt();

        sendPositionJson(context, player, playerPos, x, y, z, sceneId);
    }

    private void sendPositionJson(Context context, Player player, Position playerPos, double x, double y, double z, int sceneId) {
        JsonObject oldVal = new JsonObject();
        oldVal.addProperty("x", playerPos.getX());
        oldVal.addProperty("y", playerPos.getY());
        oldVal.addProperty("z", playerPos.getZ());
        String commandBuilder = String.format("tp %f %f %f %d @%d", x, y, z, sceneId, player.getUid());

        CommandMap.getInstance().invoke(player, player, commandBuilder);
        JsonObject val = new JsonObject();
        val.addProperty("x", playerPos.getX());
        val.addProperty("y", playerPos.getY());
        val.addProperty("z", playerPos.getZ());
        JsonObject data = new JsonObject();
        data.add("oldVal", oldVal);
        data.add("val", val);
        ResponseBuilder.buildOperationSuccess("set", "pos", data).send(context);
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
        return true;
    }

    @Override
    public ArgumentParser getGetArgumentParser() {
        return null;
    }

    @Override
    public ArgumentParser getSetArgumentParser() {
        ArgumentInfo xArgInfo = new ArgumentInfo("x", "float|string");

        xArgInfo.setDescription(Language.translate("webapi.player.commands.pos.args.x"));
        xArgInfo.setDefaultValue("~");

        ArgumentInfo yArgInfo = new ArgumentInfo("y", "float|string");
        yArgInfo.setDescription(Language.translate("webapi.player.commands.pos.args.y"));
        yArgInfo.setDefaultValue("~");

        ArgumentInfo zArgInfo = new ArgumentInfo("z", "float|string");
        zArgInfo.setDescription(Language.translate("webapi.player.commands.pos.args.z"));
        zArgInfo.setDefaultValue("~");

        ArgumentInfo sceneIdArgInfo = new ArgumentInfo("sceneId", "int|string");
        sceneIdArgInfo.setDescription(Language.translate("webapi.player.commands.pos.args.sceneId"));
        sceneIdArgInfo.setDefaultValue("~");


        return new ArgumentParser(xArgInfo, yArgInfo, zArgInfo, sceneIdArgInfo);
    }

    @Override
    public ArgumentParser getAddArgumentParser() {
        return getSetArgumentParser();
    }

    @Override
    public boolean noTarget() {
        return false;
    }
}
