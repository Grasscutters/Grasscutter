package emu.grasscutter.server.webapi.player.playerfinder;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerFinder
{
    Map<Integer, Player> players;

    private PlayerFinder()
    {
        refresh();
    }

    public void refresh()
    {
        synchronized (PlayerFinder.class)
        {
            players = new HashMap<>();
            var playerList = Grasscutter.getGameServer().getPlayers().values().stream().toList();

            for(var player: playerList)
            {
                players.put(player.getUid(), player);
            }
        }

    }

    public PlayerFindResult findPlayerById(int playerId)
    {
        refresh();
        if(!players.containsKey(playerId))
        {
            var result = new PlayerFindResult();
            result.setFindState(PlayerFindState.NoSuchPlayerId);
            return result;
        }
        var result = new PlayerFindResult(players.get(playerId));
        result.setFindState(PlayerFindState.Success);
        return result;
    }

    public PlayerFindResult findPlayerByName(String name)
    {
        refresh();
        var matchedPlayer = players.values().stream().takeWhile(p -> p.getNickname().equals(name));
        /*if(matchedPlayer.count() > 1)
        {
            return new PlayerFindResult(matchedPlayer.toList(), PlayerFindState.Success);
        }*/
        return new PlayerFindResult(matchedPlayer.toList(), PlayerFindState.Success);
    }

    public PlayerFindResult findPlayersByName(String name)
    {
        refresh();
        PlayerFindState findState = PlayerFindState.Success;
        var matchedPlayer = players.values().stream().takeWhile(p -> p.getNickname().equals(name));
        if(matchedPlayer.findAny().isEmpty())
        {
            findState = PlayerFindState.NoSuchPlayerName;
        }
        return new PlayerFindResult(matchedPlayer.toList(), findState);
    }
    static final Object staticLocker = new Object();

    static volatile PlayerFinder playerFinder;
    public static PlayerFinder getInstance()
    {
        if(playerFinder == null)
        {
            synchronized (staticLocker)
            {
                if(playerFinder == null)
                {
                    playerFinder = new PlayerFinder();
                }
            }
        }
        return playerFinder;
    }

    public static PlayerFinder createNewInstance()
    {
        return playerFinder = new PlayerFinder();
    }
}
