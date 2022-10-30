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
import emu.grasscutter.utils.Position;
import io.javalin.http.Context;


@RequestRoute(routes = "pos,position")
public class PositionInfoRequestHandler implements PlayerInfoRequestHandler
{
    @Override
    public void getAttribute(PlayerInfoRequestData requestData, Context context)
    {
        Player p = requestData.getPlayer();
        if(p == null)
        {
            ResponseBuilder.buildNormalError("Player not found", null).send(context);
            return;
        }

        var pos = p.getPosition();
        if(pos == null)
        {
            ResponseBuilder.buildOperationSuccess("get", "pos", null).send(context);
        }

        ResponseBuilder.buildOperationSuccess("get", "pos", pos).send(context);
    }

    @Override
    public void setAttribute(PlayerInfoRequestData requestData, Context context)
    {
        var args = getSetArgumentParser().parse(requestData.getData());
        if(args == null || (!args.containsKey("x") && !args.containsKey("y") && !args.containsKey("z")))
        {
            ResponseBuilder.buildArgumentMissing("Ignoring unused arguments is allowed.", getGetArgumentParser()).send(context);
            return;
        }
        var player = requestData.getPlayer();
        var playerPos = player.getPosition();
        float x = playerPos.getX(), y = playerPos.getY(), z = playerPos.getZ();
        int sceneId = player.getSceneId();
        if(args.containsKey("x"))
        {
            x = Float.parseFloat(args.get("x"));
        }

        if(args.containsKey("y"))
        {
            y = Float.parseFloat(args.get("y"));
        }

        if(args.containsKey("z"))
        {
            z = Float.parseFloat(args.get("z"));
        }

        if(args.containsKey("sceneId"))
        {
            sceneId = (int)Float.parseFloat(args.get("sceneId"));
        }

        sendPositionJson(context, player, playerPos, x, y, z, sceneId);

    }

    @Override
    public void addAttribute(PlayerInfoRequestData requestData, Context context)
    {
        var argParser = getAddArgumentParser();
        var args = argParser.parse(requestData.getData());
        if(args == null)
        {
            ResponseBuilder.buildArgumentMissing("Ignoring unused arguments is allowed.", getAddArgumentParser()).send(context);
            return;
        }
        var player = requestData.getPlayer();
        var playerPos = player.getPosition();
        double x = playerPos.getX(), y = playerPos.getY(), z = playerPos.getZ();
        int sceneId = player.getSceneId();
        if(!argParser.isDefaultValue("x"))
        {
            x += Float.parseFloat(args.get("x"));
        }

        if(!argParser.isDefaultValue("y"))
        {
            y += Float.parseFloat(args.get("y"));
        }

        if(!argParser.isDefaultValue("z"))
        {
            z += Float.parseFloat(args.get("z"));
        }

        if(!argParser.isDefaultValue("sceneId"))
        {
            sceneId = Integer.parseInt(args.get("sceneId"));
        }

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
        return true;
    }

    @Override
    public ArgumentParser getGetArgumentParser()
    {
        return null;
    }

    @Override
    public ArgumentParser getSetArgumentParser()
    {
        ArgumentParser parser = new ArgumentParser();
        ArgumentInfo xArgInfo = new ArgumentInfo("x", "float");
        xArgInfo.setDescription("x");
        xArgInfo.setDefaultValue("~");

        ArgumentInfo yArgInfo = new ArgumentInfo("y", "float");
        yArgInfo.setDescription("y");
        yArgInfo.setDefaultValue("~");

        ArgumentInfo zArgInfo = new ArgumentInfo("z", "float");
        zArgInfo.setDescription("z");
        zArgInfo.setDefaultValue("~");

        ArgumentInfo sceneIdArgInfo = new ArgumentInfo("sceneId", "int");
        sceneIdArgInfo.setDescription("场景Id");
        sceneIdArgInfo.setDefaultValue("~");

        parser.addArgument(xArgInfo);
        parser.addArgument(yArgInfo);
        parser.addArgument(zArgInfo);
        parser.addArgument(sceneIdArgInfo);
        return parser;
    }

    @Override
    public ArgumentParser getAddArgumentParser()
    {
        return getSetArgumentParser();
    }

    @Override
    public boolean noTarget()
    {
        return false;
    }
}
