package emu.grasscutter.server.webapi.player.playerfinder;

import com.google.gson.JsonObject;
import emu.grasscutter.game.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerFindResult {
    Map<Integer, Player> players = new HashMap<>();


    PlayerFindState findState;
    public PlayerFindResult(List<Player> players, PlayerFindState findState) {
        for(var player : players) {
            this.players.put(player.getUid(), player);
        }
        this.findState = findState;
    }

    public PlayerFindResult(Player... players) {
        for(var player : players) {
            this.players.put(player.getUid(), player);
        }
    }

    public Player findPlayerById(int playerId) {
        return players.get(playerId);
    }

    public Player findPlayerByName(String name) {
        var matchedPlayer = players.values().stream().takeWhile(p -> p.getNickname().equals(name));
        if(matchedPlayer.count() > 1) {
            throw new IllegalStateException("More one players have this name");
        }
        return matchedPlayer.toList().get(0);
    }

    public List<Player> findPlayersByName(String name) {
        var matchedPlayer = players.values().stream().takeWhile(p -> p.getNickname().equals(name));
        return matchedPlayer.toList();
    }

    public boolean foundPlayer()
    {
        return players.size() > 0;
    }

    public Player getFirstPlayer() {
        if(getFindState() != PlayerFindState.SUCCESS || !foundPlayer()) {
            return null;
        }
        return players.values().stream().toList().get(0);
    }

    public PlayerFindState getFindState() {
        return findState;
    }

    public void setFindState(PlayerFindState findState) {
        this.findState = findState;
    }
    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public JsonObject getJsonObject() {
        JsonObject data = new JsonObject();
        data.addProperty("playerFindState", getFindState().toString());
        data.addProperty("foundPlayerCount", players.size());
        return data;
    }

}
