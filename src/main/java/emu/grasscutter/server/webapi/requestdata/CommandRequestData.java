package emu.grasscutter.server.webapi.requestdata;

import com.google.gson.annotations.SerializedName;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.webapi.player.playerfinder.PlayerFindResult;
import emu.grasscutter.server.webapi.player.playerfinder.PlayerFinder;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class CommandRequestData {
    @SerializedName("type")
    String commandType;

    int executorId;

    String executorName;

    int targetId;

    String targetName;

    HashMap<?,?> data;

    RequestJson requestJson;

    public RequestJson getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(RequestJson requestJson) {
        if(this.requestJson == null && requestJson != null) {
            this.requestJson = requestJson;
        }
    }


    public Player getExecutor() {
        return tryGetPlayer(executorId, executorName);
    }

    public Player getTarget() {
        return tryGetPlayer(targetId, targetName);
    }

    @Nullable
    static Player tryGetPlayer(int targetPlayerId, String targetPlayerName) {
        PlayerFindResult findResult;
        if(targetPlayerId != -1) {
            findResult = PlayerFinder.getInstance().findPlayerById(targetPlayerId);
            if(findResult.foundPlayer()) {
                return findResult.getFirstPlayer();
            }
        }
        else if(targetPlayerName != null) {
            findResult = PlayerFinder.getInstance().findPlayerByName(targetPlayerName);
            if(findResult.foundPlayer()) {
                return findResult.getFirstPlayer();
            }
        }

        return null;
    }

    public String getCommandType() {
        return commandType;
    }

    public HashMap<?, ?> getData() {
        return data;
    }
}
