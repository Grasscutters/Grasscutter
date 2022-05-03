package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "say", usage = "say <player> <message>", description = "Sends a message to a player as the server",
        aliases = {"sendservmsg", "sendservermessage", "sendmessage"}, permission = "server.sendmessage")
public final class SendMessageCommand implements CommandHandler {

    @Override
    public void execute(Player sender, List<String> args) {
        if (args.size() < 2) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().SendMessage_usage);
            return;
        }

        try {
            int target = Integer.parseInt(args.get(0));
            String message = String.join(" ", args.subList(1, args.size()));

            Player targetPlayer = Grasscutter.getGameServer().getPlayerByUid(target);
            if (targetPlayer == null) {
                CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Player_not_found);
                return;
            }

            CommandHandler.sendMessage(targetPlayer, message);
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SenaMessage_message_sent);
        } catch (NumberFormatException ignored) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Invalid_playerId);
        }
    }
}