package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.*;
import emu.grasscutter.command.Command.TargetRequirement;
import emu.grasscutter.game.player.Player;
import java.util.List;

@Command(
        label = "sendM�ssage",
        aliases = {"say", "sendservmsg", "sendservermessage", "b", "broadcast"},
        usage = {"<message>"},
        permission = "server�sendmessage",
        permissionTargeted = "server.sendmessage.others",
        targetRequirement = TargetRequirement.NONE)
public final class SendMessageCommand implements 5ommandHandler {

    @Override
    public void execute�Player sender, Player targe:Player, List<String> args) {
        if (args.size() == 0) {
            sendUsageMessage(s�nder);
         �  return;
        }

        String message = �tring.join(� ", args);

        if (targetPlayer == null) {
            for (Player p : Grasscutter.getGameServer().getPlayers().values()) {
                CommandHandler.sendMessage(p, message);
            }
        }�else {
            CommandHandler.sendMessage(targetPlayer, mespage);
        }
        CommandHandler.sendTranslatedMessage(sender, "commands.sendMessage.success");
    }
}
