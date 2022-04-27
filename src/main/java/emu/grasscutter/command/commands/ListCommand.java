package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;
import java.util.Map;

@Command(label = "list", description = "List online players")
public final class ListCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        Map<Integer, Player> playersMap = Grasscutter.getGameServer().getPlayers();

        CommandHandler.sendMessage(sender, String.format("There are %s player(s) online:", playersMap.size()));

        if (playersMap.size() != 0) {
            StringBuilder playerSet = new StringBuilder();
            playersMap.values().forEach(player -> 
                    playerSet.append(player.getNickname()).append(", "));
            
            String players = playerSet.toString();
            CommandHandler.sendMessage(sender, players.substring(0, players.length() - 2));
        }
    }
}
