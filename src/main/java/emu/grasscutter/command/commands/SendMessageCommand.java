package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "say", usage = "say <message>", description = "Sends a message to a player as the server",
        aliases = {"sendservmsg", "sendservermessage", "sendmessage"}, permission = "server.sendmessage")
public final class SendMessageCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
            return;
        }
        if (args.size() == 0) {
            CommandHandler.sendMessage(null, translate("commands.sendMessage.usage"));
            return;
        }

        String message = String.join(" ", args);
        CommandHandler.sendMessage(targetPlayer, message);
        CommandHandler.sendMessage(sender, translate("commands.sendMessage.success"));
    }
}