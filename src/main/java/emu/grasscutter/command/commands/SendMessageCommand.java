package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.*;
import emu.grasscutter.command.Command.TargetRequirement;
import emu.grasscutter.game.player.Player;
import java.util.List;

@Command(
        label = "sendMessage",
        aliases = {"say", "sendservmsg", "sendservermessage", "b", "broadcast"},
        usage = {"<message>"},
        permission = "server.sendmessage",
        permissionTargeted = "server.sendmessage.others",
        targetRequirement = TargetRequirement.NONE)
public final class SendMessageCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size() == 0) {
            sendUsageMessage(sender);
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
        CommandHandler.sendTranslatedMessage(sender, "commands.sendMessage.success");
    }
}
