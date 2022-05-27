package emu.grasscutter.command.commands;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(label = "kick", usage = "kick",
        description = "Kicks the specified player from the server (WIP)", permission = "server.kick")
public final class KickCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate("commands.execution.need_target"));
            return;
        }

        if (sender != null) {
            CommandHandler.sendMessage(sender, translate("commands.kick.player_kick_player", 
                    Integer.toString(sender.getAccount().getPlayerUid()), sender.getAccount().getUsername(),
                    Integer.toString(targetPlayer.getUid()), targetPlayer.getAccount().getUsername()));
        } else {
            CommandHandler.sendMessage(null, translate("commands.kick.server_kick_player", Integer.toString(targetPlayer.getUid()), targetPlayer.getAccount().getUsername()));
        }

        targetPlayer.getSession().close();
    }
}
