package emu.grasscutter.server.webapi.requestdata;

import com.google.gson.annotations.SerializedName;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.webapi.player.playerfinder.PlayerFindResult;
import emu.grasscutter.server.webapi.player.playerfinder.PlayerFinder;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class CommandRequestData
{
    @SerializedName("type")
    String commandType;

    @SerializedName(value = "command", alternate = {"cmd"})
    String command;

    int playerId;

    String playerName;

    int targetPlayerId;

    String targetPlayerName;

    HashMap<?,?> data;

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

    public Player getPlayer()
    {
        return tryGetPlayer(playerId, playerName);
    }

    public Player getTargetPlayer()
    {
        return tryGetPlayer(targetPlayerId, targetPlayerName);
    }

    @Nullable
    static Player tryGetPlayer(int targetPlayerId, String targetPlayerName) {
        PlayerFindResult findResult;
        if(targetPlayerId != -1)
        {
            findResult = PlayerFinder.getInstance().findPlayerById(targetPlayerId);
            if(findResult.foundPlayer())
            {
                return findResult.getFirstPlayer();
            }
        }
        else if(targetPlayerName != null)
        {
            findResult = PlayerFinder.getInstance().findPlayerByName(targetPlayerName);
            if(findResult.foundPlayer())
            {
                return findResult.getFirstPlayer();
            }
        }

        return null;
    }

    public String getCommand()
    {
        return command;
    }

    public String getCommandType()
    {
        return commandType;
    }

    public HashMap<?, ?> getData()
    {
        return data;
    }
}
