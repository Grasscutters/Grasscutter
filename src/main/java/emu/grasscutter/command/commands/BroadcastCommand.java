package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "broadcast", usage = "broadcast <message>",
        description = "Sends a message to all the players", aliases = {"b"}, permission = "server.broadcast")
public final class BroadcastCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() < 1) {
            CommandHandler.sendMessage(sender, translate("commands.broadcast.command_usage"));
            return;
        }

        String message = String.join(" ", args.subList(0, args.size()));

        for (Player p : Grasscutter.getGameServer().getPlayers().values()) {
            CommandHandler.sendMessage(p, message);
        }

        CommandHandler.sendMessage(sender, translate("commands.broadcast.message_sent"));
    }
}
