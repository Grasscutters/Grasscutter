package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "say", usage = "say <message>", description = "Sends a message to a player as the server",
        aliases = {"sendservmsg", "sendservermessage", "sendmessage"}, permission = "server.sendmessage")
public final class SendMessageCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, Grasscutter.getLanguage().Target_needed);
            return;
        }
        if (args.size() == 0) {
            CommandHandler.sendMessage(null, Grasscutter.getLanguage().SendMessage_usage);
            return;
        }

        String message = String.join(" ", args);
        CommandHandler.sendMessage(targetPlayer, message);
        CommandHandler.sendMessage(sender, Grasscutter.getLanguage().SenaMessage_message_sent);
    }
}