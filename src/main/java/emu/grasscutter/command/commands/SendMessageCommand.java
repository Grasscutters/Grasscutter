package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.command.Command.TargetRequirement;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "sendmessage", usage = "sendmessage <message>",
    aliases = {"say", "sendservmsg", "sendservermessage", "b", "broadcast"}, permission = "server.sendmessage", permissionTargeted = "server.sendmessage.others", description = "commands.sendMessage.description", targetRequirement = TargetRequirement.NONE)
public final class SendMessageCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() == 0) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.sendMessage.usage"));
            return;
        }

        String message = String.join(" ", args);

        if (targetPlayer == null) {
            for (Player p : Grasscutter.getGameServer().getPlayers().values()) {
                CommandHandler.sendMessage(p, message);
            }
        } else {
            CommandHandler.sendMessage(targetPlayer, message);
        }
        CommandHandler.sendMessage(sender, translate(sender, "commands.sendMessage.success"));
    }
}
