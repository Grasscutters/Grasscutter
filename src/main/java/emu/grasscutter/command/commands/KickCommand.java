package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "kick", usage = "kick", aliases = {"restart"}, permissionTargeted = "server.kick", description = "commands.kick.description")
public final class KickCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (sender != null) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.kick.player_kick_player",
                Integer.toString(sender.getUid()), sender.getAccount().getUsername(),
                Integer.toString(targetPlayer.getUid()), targetPlayer.getAccount().getUsername()));
        } else {
            CommandHandler.sendMessage(null, translate(sender, "commands.kick.server_kick_player", Integer.toString(targetPlayer.getUid()), targetPlayer.getAccount().getUsername()));
        }

        targetPlayer.getSession().close();
    }
}
