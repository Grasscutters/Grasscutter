package emu.grasscutter.server.webapi.player.tools;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import emu.grasscutter.Grasscutter;

public class PlayerTools {
    public static JsonObject getAllOnlinePlayer() {
        JsonObject data = new JsonObject();
        JsonArray playersJson = new JsonArray();
        data.add("players", playersJson);
        for (var player : Grasscutter.getGameServer().getPlayers().entrySet()) {
            JsonObject playerJson = new JsonObject();
            playerJson.addProperty("playerId", player.getKey());
            playerJson.addProperty("nickName", player.getValue().getNickname());
            playersJson.add(playerJson);
        }
        return data;
    }
}
