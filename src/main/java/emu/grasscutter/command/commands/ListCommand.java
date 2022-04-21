package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.GenshinPlayer;

import java.util.List;
import java.util.Map;

@Command(label = "list", description = "List online players")
public class ListCommand implements CommandHandler {

    @Override
    public void execute(GenshinPlayer sender, List<String> args) {
        Map<Integer, GenshinPlayer> playersMap = Grasscutter.getGameServer().getPlayers();

        CommandHandler.sendMessage(sender, String.format("There are %s player(s) online:", playersMap.size()));

        if (playersMap.size() != 0) {
            StringBuilder playerSet = new StringBuilder();

            for (Map.Entry<Integer, GenshinPlayer> entry : playersMap.entrySet()) {
                playerSet.append(entry.getValue().getNickname());
                playerSet.append(", ");
            }

            String players = playerSet.toString();

            CommandHandler.sendMessage(sender, players.substring(0, players.length() - 2));
        }
    }
}
