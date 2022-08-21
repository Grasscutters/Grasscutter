package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

@Command(label = "kick", aliases = {"restart"}, permissionTargeted = "server.kick")
public final class KickCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (sender != null) {
            CommandHandler.sendTranslatedMessage(sender, "commands.kick.player_kick_player",
                sender.getUid(), sender.getAccount().getUsername(),
                targetPlayer.getUid(), targetPlayer.getAccount().getUsername());
        } else {
            CommandHandler.sendTranslatedMessage(sender, "commands.kick.server_kick_player",
                targetPlayer.getUid(), targetPlayer.getAccount().getUsername());
        }

        targetPlayer.getSession().close();
    }
}
