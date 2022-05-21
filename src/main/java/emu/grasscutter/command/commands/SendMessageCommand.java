package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "say", usage = "say <message>",
        aliases = {"sendservmsg", "sendservermessage", "sendmessage"}, permission = "server.sendmessage", permissionTargeted = "server.sendmessage.others", description = "commands.sendMessage.description")
public final class SendMessageCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() == 0) {
            CommandHandler.sendMessage(null, translate(sender, "commands.sendMessage.usage"));
            return;
        }

        String message = String.join(" ", args);
        CommandHandler.sendMessage(targetPlayer, message);
        CommandHandler.sendMessage(sender, translate(sender, "commands.sendMessage.success"));
    }
}
