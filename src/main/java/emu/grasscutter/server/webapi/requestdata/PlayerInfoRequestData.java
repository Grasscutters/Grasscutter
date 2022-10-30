package emu.grasscutter.server.webapi.requestdata;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import emu.grasscutter.game.player.Player;

public class PlayerInfoRequestData
{
    int playerId = -1;
    String playerName;

    @SerializedName("op")
    String operation;

    @SerializedName("attr")
    String attribute;

    JsonObject data;
    RequestJson requestJson;

    public RequestJson getRequestJson()
    {
        return requestJson;
    }

    public void setRequestJson(RequestJson requestJson)
    {
        if(this.requestJson == null && requestJson != null)
        {
            this.requestJson = requestJson;
        }
    }

    public int getPlayerId()
    {
        return playerId;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public String getOperation()
    {
        return operation;
    }

    public String getAttribute()
    {
        return attribute;
    }

    public JsonObject getData()
    {
        return data;
    }

    public Player getPlayer()
    {
        return CommandRequestData.tryGetPlayer(playerId, playerName);
    }

}
